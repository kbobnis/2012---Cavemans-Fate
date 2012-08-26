package com.kprojekt.cavemansfate.MVC.cave.controller;

import com.kprojekt.cavemansfate.MVC.cave.model.CaveModel;

/**
 * @author Philon 
 */
public class CaveController
{
	private final CaveModel caveModel;
	private final CavemanController cavemanController;

	public CaveController( CaveModel caveModel )
	{
		this.caveModel = caveModel;
		this.cavemanController = new CavemanController( caveModel.getCaveman() );
	}

	public CaveModel getModel()
	{
		return this.caveModel;
	}

	public CavemanController getCavemanController()
	{
		return this.cavemanController;
	}

}
