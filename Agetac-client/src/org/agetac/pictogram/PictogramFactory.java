package org.agetac.pictogram;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.pictogram.impl.Pictogram;
import org.agetac.pictogram.sign.IPictogram;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;


public class PictogramFactory {

	private static final String PICTO_RED_UP = "red_up";
	private static final String PICTO_RED_DOWN = "red_down";
	private static final String PICTO_GREEN_UP = "green_up";
	private static final String PICTO_GREEN_DOWN = "green_down";
	private static final String PICTO_BLUE_UP = "blue_up";
	private static final String PICTO_BLUE_DOWN = "blue_down";
	private static final String PICTO_BLUE_GRP = "blue_grp";
	private static final String PICTO_BLUE_GRPS = "blue_grps";
	private static final String PICTO_BLUE_SINGLE = "blue_single";
	private static final String PICTO_GREEN_GRP = "green_grp";
	private static final String PICTO_GREEN_GRPS = "green_grps";
	private static final String PICTO_GREEN_SINGLE = "green_single";
	private static final String PICTO_RED_DOTTED_SINGLE = "red_dotted_single";
	private static final String PICTO_RED_DOTTED_GRP = "red_dotted_grp";
	private static final String PICTO_RED_GRP = "red_grp";
	private static final String PICTO_RED_GRPS = "red_grps";
	private static final String PICTO_RED_SINGLE = "red_single";
	
	private static PictogramFactory instance;
	private Resources res;
	private ArrayList<IPictogram> pictos;
	
	private PictogramFactory(Context context) {
		this.res = context.getResources();
		pictos = new ArrayList<IPictogram>();
		
		Options options = new Options();
		options.inPurgeable = false;
		pictos.add(new Pictogram(PICTO_RED_UP, BitmapFactory.decodeResource(res, R.drawable.picto_red_up, options)));
		pictos.add(new Pictogram(PICTO_RED_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_red_down, options)));
		pictos.add(new Pictogram(PICTO_GREEN_UP, BitmapFactory.decodeResource(res, R.drawable.picto_green_up)));
		pictos.add(new Pictogram(PICTO_GREEN_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_green_down)));
		pictos.add(new Pictogram(PICTO_BLUE_UP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_up)));
		pictos.add(new Pictogram(PICTO_BLUE_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_blue_down)));
		pictos.add(new Pictogram(PICTO_BLUE_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grp)));
		pictos.add(new Pictogram(PICTO_BLUE_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grps)));
		pictos.add(new Pictogram(PICTO_BLUE_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_blue_single)));
		pictos.add(new Pictogram(PICTO_GREEN_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_green_grp)));
		pictos.add(new Pictogram(PICTO_GREEN_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_green_grps)));
		pictos.add(new Pictogram(PICTO_GREEN_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_green_single)));
		pictos.add(new Pictogram(PICTO_RED_DOTTED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_single)));
		pictos.add(new Pictogram(PICTO_RED_DOTTED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_grp)));
		pictos.add(new Pictogram(PICTO_RED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_grp)));
		pictos.add(new Pictogram(PICTO_RED_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_red_grps)));
		pictos.add(new Pictogram(PICTO_RED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_single)));
	}
	
	public static PictogramFactory getInstance(Context context) {
		if (instance == null) {
			instance = new PictogramFactory(context);
		}
		return instance;
	}

	public ArrayList<IPictogram> getPictograms() {
		return pictos;
	}
	
	public String[] getPictogramNames() {
		String[] names = new String[pictos.size()];
		for (int i=0; i<pictos.size(); i++) {
			names[i] = pictos.get(i).getName();
		}
		return names;
	}
}
