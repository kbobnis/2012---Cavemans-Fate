package com.kprojekt.cavemansfate.MVC.cave.model;

import com.badlogic.gdx.math.Vector3;
import com.kprojekt.cavemansfate.MVC.cave.actions.CavemanAction;

/**
 * @author Philon
 */
public class CavemanState
{
	public enum SIDES
	{
		UP, DOWN, LEFT, RIGHT, UP_RIGHT, UP_LEFT, MIDDLE, DOWN_LEFT, DOWN_RIGHT, CENTER;

		public static Vector3 add( SIDES side, Vector3 pos, boolean yDown )
		{
			Vector3 res = new Vector3( pos.x, pos.y, 0 );
			switch( side )
			{
				case UP:
				{
					res.y += yDown ? -1 : 1;
					break;
				}
				case DOWN:
				{
					res.y += yDown ? 1 : -1;
					break;
				}
				case LEFT:
				{
					res.x--;
					break;
				}
				case RIGHT:
				{
					res.x++;
					break;
				}
				case UP_RIGHT:
				{
					res.x++;
					res.y += yDown ? -1 : 1;
					break;
				}
				case UP_LEFT:
				{
					res.x--;
					res.y += yDown ? -1 : 1;
					break;
				}
				case DOWN_RIGHT:
				{
					res.x++;
					res.y += yDown ? 1 : -1;
					break;
				}
				case DOWN_LEFT:
				{
					res.x--;
					res.y += yDown ? 1 : -1;
					break;
				}
				case CENTER:
				{
					break;
				}

				default:
					throw new RuntimeException( "There is no value " + side );
			}
			return res;
		}

		public static SIDES negate( SIDES side )
		{
			switch( side )
			{
				case UP:
					return DOWN;
				case DOWN:
					return UP;
				case LEFT:
					return RIGHT;
				case RIGHT:
					return LEFT;
				case UP_RIGHT:
					return DOWN_LEFT;
				case UP_LEFT:
					return DOWN_RIGHT;
				default:
					throw new RuntimeException( "THere is no negating needed for (" + side + ")." );
			}
		}
	}

	public boolean cavemanSelected;
	public SIDES sideSelected;
	public CavemanAction actionSelected;
	public float shadowOffsetX;
	public float shadowOffsetY;

	public void resetState()
	{
		sideSelected = null;
		actionSelected = null;
		cavemanSelected = false;

	}

	public void cavemanSelected( boolean b )
	{
		this.cavemanSelected = b;
	}
}
