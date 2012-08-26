package com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents;

/**
 * @author Philon
 */
public class Trigger
{
	public enum ACTIVATE_ACTION
	{
		JUST_ON, FINGER_DOWN_NOT_ON_CAVEMAN, FINGER_DOWN_ON_CAVEMAN, FINGER_UP, FINGER_DOWN_ANYWHERE, PICKUP_TILE, BE_ON_TILE, NEVER, ENTER_TELEPORT;

		public boolean willActivate( ACTIVATE_ACTION activateAction )
		{
			switch( this )
			{
				case JUST_ON:
					return this.doIActivateJUST_ON( activateAction );
				case FINGER_DOWN_ANYWHERE:
					return this.doIActivateFINGER_DOWN_ANYWHERE( activateAction );
				case FINGER_DOWN_NOT_ON_CAVEMAN:
				case PICKUP_TILE:
				case ENTER_TELEPORT:
				case BE_ON_TILE:
					return this == activateAction;
				case NEVER:
					return false;
				default:
					throw new RuntimeException( "There is no activate for (" + this + ") defined. " );
			}
		}

		private boolean doIActivateFINGER_DOWN_ANYWHERE( ACTIVATE_ACTION activateAction )
		{
			switch( activateAction )
			{
				case FINGER_DOWN_ANYWHERE:
				case FINGER_DOWN_NOT_ON_CAVEMAN:
				case FINGER_DOWN_ON_CAVEMAN:
					return true;
				default:
					return false;
			}
		}

		private boolean doIActivateJUST_ON( ACTIVATE_ACTION activateAction )
		{
			return true;
		}

		public static ACTIVATE_ACTION fromString( String triggerAction )
		{
			//JUST_ON, FINGER_DOWN_NOT_ON_CAVEMAN, FINGER_DOWN_ON_CAVEMAN, FINGER_UP, FINGER_DOWN_ANYWHERE, PICKUP_TILE, BE_ON_TILE, NEVER, ENTER_TELEPORT;
			if( triggerAction.equalsIgnoreCase( "JUST_ON" ) )
			{
				return JUST_ON;
			}
			if( triggerAction.equalsIgnoreCase( "BE_ON_TILE" ) )
			{
				return BE_ON_TILE;
			}
			if( triggerAction.equalsIgnoreCase( "ENTER_TELEPORT" ) )
			{
				return ENTER_TELEPORT;
			}
			if( triggerAction.equalsIgnoreCase( "PICKUP_TILE" ) )
			{
				return PICKUP_TILE;
			}

			throw new RuntimeException( "There is no trigger for string: (" + triggerAction + ")" );
		}
	}

	protected boolean active;

	protected final ACTIVATE_ACTION activateAction;

	public Trigger( ACTIVATE_ACTION activateAction )
	{
		this.activateAction = activateAction;
	}

	public boolean isActivated()
	{
		this.tryToActivate( ACTIVATE_ACTION.JUST_ON );
		return this.active;
	}

	public void tryToActivate( ACTIVATE_ACTION triggerAction )
	{
		if( !this.active )
		{
			this.active = this.activateAction.willActivate( triggerAction );
		}
	}

	/**
	 * This method is to be overriden, but i don't want this class to be abstract
	 */
	public void tryToActivate( ACTIVATE_ACTION triggerAction, int newX, int newY )
	{
		this.tryToActivate( triggerAction );
	}

}
