package com.kprojekt.cavemansfate.MVC.cave.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.kprojekt.cavemansfate.MVC.cave.actions.CavemanAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.DiggAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.MoveAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.NoDiggingWhenPickuped;
import com.kprojekt.cavemansfate.MVC.cave.actions.NotWhileSwimmingAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.NothingToDigg;
import com.kprojekt.cavemansfate.MVC.cave.actions.PutTileAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.ToHardRock;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Events;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.cavemansfate.core.Core;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon
 */
public class CavemanModel
{
	public Vector3 pos;
	private MyTiledMap tiledMap;
	private int tilePickedUpId;
	private List<Weapon> weaponsEquipped = new ArrayList<Weapon>();
	private int cavemansDiggPower;
	private Map<String, CavemanAction> actions = new HashMap<String, CavemanAction>();
	private Events events;
	private boolean enteredTeleport;

	public CavemanModel( MyTiledMap tiledMap, Events events )
	{
		this.init( tiledMap, events );
	}

	private void init( MyTiledMap tiledMap, Events events )
	{
		this.tiledMap = tiledMap;
		if( events == null )
		{
			events = new Events();
		}
		this.events = events;
		int cavemanId = -1;
		try
		{
			cavemanId = this.tiledMap.getId( MyTiledMap.KEY_NAME, MyTiledMap.PLAYER_NAME, MyTiledMap.PLAYER_LAYER_NAME );
		}
		catch( Exception e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		String cavemansDiggPowerString = this.tiledMap.getTileProperty( cavemanId, MyTiledMap.POWER_NAME );
		this.cavemansDiggPower = Integer.parseInt( cavemansDiggPowerString );
		try
		{
			pos = this.tiledMap.getFirstPos( cavemanId, MyTiledMap.PLAYER_LAYER_NAME );
		}
		catch( Exception e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		this.tiledMap.removeTile( pos.x, pos.y, MyTiledMap.PLAYER_LAYER_NAME );
		this.tilePickedUpId = 0;

		actions.put( DiggAction.name, new DiggAction() );
		actions.put( PutTileAction.name, new PutTileAction() );
		actions.put( MoveAction.name, new MoveAction() );
		//actions.put( NotWhileSwimmingAction.name, new NotWhileSwimmingAction() );
		//actions.put( NoDiggingWhenPickuped.name, new NoDiggingWhenPickuped() );
		//actions.put( ToHardRock.name, new ToHardRock() );
		//actions.put( NothingToDigg.name, new NothingToDigg() );

		//this.fall();
	}

	public void fall()
	{
		//maybe after the move, the caveman will fall, lets check this
		while( !isSwimming()
				&& (this.canMoveOnBackground( (int)pos.x, (int)pos.y + 1, MyTiledMap.WALKABLE_NAME, true ) || this.canMoveOnBackground(
						(int)pos.x, (int)pos.y + 1, MyTiledMap.SWIMMABLE_NAME, true )) )
		{
			//if he has picked up tile 
			if( this.hasTilePickedUp() )
			{
				try
				{
					//and falling into water
					Boolean swimmable = Boolean.parseBoolean( this.tiledMap.getValue( (int)pos.x, (int)pos.y + 1,
							MyTiledMap.SWIMMABLE_NAME, MyTiledMap.BACKGROUND_LAYER_NAME ) );
					if( swimmable )
					{
						//move caveman into water 
						this.moveCaveman( (int)pos.x, (int)pos.y + 1 );
						//and leave tile
						PutTileAction putTile = new PutTileAction();
						putTile.doAction( this, this.tiledMap, SIDES.UP );
						return;
					}
				}
				catch( Exception e )
				{
					//can not move outside the game map
					return;
				}
			}
			this.moveCaveman( (int)pos.x, (int)pos.y + 1 );
		}
	}

	public boolean hasTilePickedUp()
	{
		return this.tilePickedUpId != 0;
	}

	/**
	 * @return false if asked to dig air.
	 * @return false if in water
	 */
	public boolean canDigg( SIDES side )
	{
		if( side == SIDES.UP_RIGHT || side == SIDES.UP_LEFT || this.isSwimming() || side == SIDES.DOWN_LEFT
				|| side == SIDES.DOWN_RIGHT )
		{
			return false;
		}

		return canDigInner( side );
	}

	private boolean canDigInner( SIDES side )
	{
		Vector3 tilePosX = SIDES.add( side, this.pos, true );

		int hardness;
		try
		{
			int id = this.tiledMap.getId( (int)tilePosX.x, (int)tilePosX.y, MyTiledMap.BACKGROUND_LAYER_NAME );
			String swimmableString = this.tiledMap.getTileProperty( id, MyTiledMap.SWIMMABLE_NAME );
			if( id == 0 || (swimmableString != null && Boolean.parseBoolean( swimmableString )) ) //0 is nothing, just air, can not dig, water, no dig
			{
				return false;
			}
			hardness = Integer.parseInt( this.tiledMap.getTileProperty( id, MyTiledMap.HARDNESS_NAME ) );
		}
		catch( Exception e ) //out of bonds, can not dig
		{
			return false;
		}

		int power = this.getBestWeaponsPower();

		return power >= hardness && !this.hasTilePickedUp();
	}

	public boolean isSwimming()
	{
		try
		{
			String value = this.tiledMap.getValue( (int)pos.x, (int)pos.y, MyTiledMap.SWIMMABLE_NAME,
					MyTiledMap.BACKGROUND_LAYER_NAME );
			if( value == null )
			{
				return false;
			}
			return (Boolean.parseBoolean( value ));
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean canWalk( SIDES side )
	{
		return canMoveInner( side, MyTiledMap.WALKABLE_NAME, true );
	}

	public boolean canWalkOrSwim( SIDES side )
	{
		return canMoveInner( side, MyTiledMap.WALKABLE_NAME, true )
				|| canMoveInner( side, MyTiledMap.SWIMMABLE_NAME, true );
	}

	private boolean canMoveInner( SIDES side, String movable, boolean alsoAir )
	{
		Vector3 add = SIDES.add( side, this.pos, true );
		Vector3 up = SIDES.add( SIDES.UP, this.pos, true );
		Vector3 left = SIDES.add( SIDES.LEFT, this.pos, true );
		Vector3 right = SIDES.add( SIDES.RIGHT, this.pos, true );

		Vector3 tmp = null;
		switch( side )
		{
			case UP:
			case DOWN:
			case LEFT:
			case RIGHT:
			case CENTER:
				return this.canMoveOnBackground( (int)add.x, (int)add.y, movable, alsoAir );
			case UP_RIGHT:
			case UP_LEFT:
				tmp = up;
				break;
			case DOWN_LEFT:
				tmp = left;
				break;
			case DOWN_RIGHT:
				tmp = right;
				break;
			default:
				throw new RuntimeException( "There is no side " + side );
		}

		return this.canMoveOnBackground( (int)add.x, (int)add.y, movable, alsoAir )
				&& (this.canMoveOnBackground( (int)tmp.x, (int)tmp.y, MyTiledMap.WALKABLE_NAME, true ) || this.canMoveOnBackground(
						(int)tmp.x, (int)tmp.y, MyTiledMap.SWIMMABLE_NAME, true ));
	}

	public void move( SIDES side )
	{
		Vector3 newPos = SIDES.add( side, this.pos, true );
		this.moveCaveman( (int)newPos.x, (int)newPos.y );
	}

	private void moveCaveman( int newX, int newY )
	{
		//this.tiledMap.moveTile( (int)pos.x, (int)pos.y, newX, newY, MyTiledMap.PLAYER_LAYER_NAME );
		this.events.updateAll( ACTIVATE_ACTION.BE_ON_TILE, newX, newY );
		this.pos.x = newX;
		this.pos.y = newY;

		pickUpPickableItemIfAny();
		enterTeleportIfAny();
		fall();
	}

	private boolean canMoveOnBackground( int newX, int newY, String movableParameter, boolean alsoAir )
	{
		boolean canMove;
		try
		{
			int id = this.tiledMap.getId( newX, newY, MyTiledMap.BACKGROUND_LAYER_NAME );
			canMove = Boolean.parseBoolean( this.tiledMap.getValue( newX, newY, movableParameter,
					MyTiledMap.BACKGROUND_LAYER_NAME ) );
			//id == 0 has no attributes, its a 'nothing' tile, you can move there
			if( (alsoAir && id == 0) || canMove )
			{
				return true;
			}
		}
		catch( Exception e )
		{
			//nothing to do here, he just can not walk outside map
		}
		return false;
	}

	private int getBestWeaponsPower()
	{
		int bestWeaponsPower = this.cavemansDiggPower;
		for( Weapon weapon : this.weaponsEquipped )
		{
			if( weapon.power() > bestWeaponsPower )
			{
				bestWeaponsPower = weapon.power();
			}
		}
		return bestWeaponsPower;
	}

	public List<CavemanAction> getAvailableAction( SIDES side )
	{
		ArrayList<CavemanAction> theList = new ArrayList<CavemanAction>();
		//if there is no tile on the back -> checking if can be digged
		if( this.canDigg( side ) )
		{
			theList.add( this.actions.get( DiggAction.name ) );
		}
		if( this.canWalkOrSwim( side ) )
		{
			if( this.hasTilePickedUp() && side != SIDES.UP_LEFT && side != SIDES.UP_RIGHT && !isThereATeleport( side )
					&& side != SIDES.DOWN_LEFT && side != SIDES.DOWN_RIGHT && !this.canSwim( side ) )
			{
				theList.add( this.actions.get( PutTileAction.name ) );
			}

			if( side == SIDES.UP && !this.isThereATeleport( side ) && !this.canSwim( side ) )
			{
			}
			else if( side == SIDES.UP_LEFT && this.canWalk( SIDES.LEFT ) || side == SIDES.UP_RIGHT
					&& this.canWalk( SIDES.RIGHT ) )
			{
			}
			else if( !isThereATeleport( side )
					&& (side == SIDES.LEFT && this.canWalk( SIDES.DOWN_LEFT ) || side == SIDES.RIGHT
							&& this.canWalk( SIDES.DOWN_RIGHT )) )
			{

			}
			else if( this.canWalk( side )
					&& (side == SIDES.UP_LEFT && this.canSwim( SIDES.LEFT ) || (side == SIDES.UP_RIGHT && this.canSwim( SIDES.RIGHT ))) )
			{

			}
			else
			{
				theList.add( this.actions.get( MoveAction.name ) );
			}
		}

		return theList;
	}

	private boolean isThereATeleport( SIDES side )
	{
		Vector3 newPos = SIDES.add( side, this.pos, true );
		String name = this.tiledMap.getValue( (int)newPos.x, (int)newPos.y, MyTiledMap.KEY_NAME,
				MyTiledMap.TELEPORT_LAYER_NAME );

		return name != null && MyTiledMap.TELEPORT_NAME.equalsIgnoreCase( name );

	}

	public boolean pickUpTile( int tileId )
	{
		if( this.hasTilePickedUp() || tileId <= 0 )
		{
			return false;
		}
		this.tilePickedUpId = tileId;
		return true;
	}

	public int getPickupedTileId()
	{
		return this.tilePickedUpId;
	}

	public void unpackTile()
	{
		this.tilePickedUpId = 0;
	}

	public List<Weapon> getWeapons()
	{
		return this.weaponsEquipped;
	}

	public void pickUpPickableItemIfAny()
	{
		int id = 0;
		try
		{
			id = this.tiledMap.getId( (int)this.pos.x, (int)this.pos.y, MyTiledMap.PICKABLEITEMS_LAYER_NAME );
			if( id != 0 )
			{
				this.weaponsEquipped.add( new Weapon( id, this.tiledMap ) );
				this.tiledMap.removeTile( pos.x, pos.y, MyTiledMap.PICKABLEITEMS_LAYER_NAME );
			}
		}
		catch( NumberFormatException e )
		{
			throw new RuntimeException(
					"Pickaxe has thrown a number format exception, maybe there is no power declared? " + e.getMessage() );
		}
		catch( Exception e )
		{
			return;
		}
	}

	public void enterTeleportIfAny()
	{
		int id = 0;
		try
		{
			id = this.tiledMap.getId( (int)this.pos.x, (int)this.pos.y, MyTiledMap.TELEPORT_LAYER_NAME );

			if( id != 0 )
			{
				String name = this.tiledMap.getTileProperty( id, MyTiledMap.KEY_NAME );
				if( name.equalsIgnoreCase( MyTiledMap.TELEPORT_NAME ) )
				{
					this.events.updateAll( ACTIVATE_ACTION.ENTER_TELEPORT, (int)(this.pos.x), (int)(this.pos.y) );
					Core.sounds.getExitLevel().play();
					this.enteredTeleport = true;
				}
			}
		}
		catch( Exception e )
		{
			return;
		}
	}

	public boolean canSwim( SIDES side )
	{
		return this.canMoveInner( side, MyTiledMap.SWIMMABLE_NAME, false );
	}

	public boolean canDigHimselfOutOfWater( SIDES side )
	{
		return this.canDigInner( side );
	}

	public void reset( MyTiledMap tiledMap2, Events events2 )
	{
		this.weaponsEquipped.clear();
		this.enteredTeleport = false;
		try
		{
			this.init( tiledMap2, events2 );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw new RuntimeException( "Dafuq?" );
		}
	}

	public boolean isEnteredTeleport()
	{
		return this.enteredTeleport;
	}

	public MyTiledMap getMap()
	{
		return this.tiledMap;
	}

	public Events getEvents()
	{
		return this.events;
	}

	public void putTile( SIDES side )
	{
		CavemanAction cavemanAction = this.actions.get( PutTileAction.name );
		cavemanAction.doAction( this, this.getMap(), side );
	}

	public void leaveTile( SIDES negate )
	{
		PutTileAction putTile = new PutTileAction();
		putTile.doAction( this, this.getMap(), negate );

	}
}
