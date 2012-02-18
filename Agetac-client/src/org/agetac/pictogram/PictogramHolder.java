package org.agetac.pictogram;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.pictogram.impl.Color;
import org.agetac.pictogram.impl.GraphicalOverload;
import org.agetac.pictogram.impl.Pictogram;
import org.agetac.pictogram.impl.Shape;
import org.agetac.pictogram.impl.State;
import org.agetac.pictogram.sign.IPictogram;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;


public class PictogramHolder {
	
	private static final String RED_UP = "Source de feu";
	private static final String RED_DOWN = "red_down";
	private static final String GREEN_UP = "Produits chimiques";
	private static final String GREEN_DOWN = "green_down";
	private static final String BLUE_UP = "Source d'eau";
	private static final String BLUE_DOWN = "blue_down";
	private static final String BLUE_GRP = "blue_grp";
	private static final String BLUE_GRPS = "blue_grps";
	private static final String BLUE_SINGLE = "blue_single";
	private static final String GREEN_GRP = "green_grp";
	private static final String GREEN_GRPS = "green_grps";
	private static final String GREEN_SINGLE = "green_single";
	private static final String RED_DOTTED_SINGLE = "red_dotted_single";
	private static final String RED_DOTTED_GRP = "red_dotted_grp";
	private static final String RED_GRP = "red_grp";
	private static final String RED_GRPS = "red_grps";
	private static final String RED_SINGLE = "red_single";
	private static final String LINE = "Ligne";
	private static final String POINT = "Point";
	private static final String ZONE = "Zone";
	
	private static PictogramHolder instance;
	private Resources res;
	private ArrayList<IPictogram> pictos;
	
	private PictogramHolder(Context context) {
		this.res = context.getResources();
		pictos = new ArrayList<IPictogram>();
		
		Options options = new Options();
		options.inPurgeable = false;
		pictos.add(new Pictogram(RED_UP, BitmapFactory.decodeResource(res, R.drawable.picto_red_up, options), Color.RED, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE));
		pictos.add(new Pictogram(RED_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_red_down, options), Color.RED, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE));
		pictos.add(new Pictogram(GREEN_UP, BitmapFactory.decodeResource(res, R.drawable.picto_green_up), Color.GREEN, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE));
		pictos.add(new Pictogram(GREEN_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_green_down), Color.GREEN, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE));
		pictos.add(new Pictogram(BLUE_UP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_up), Color.BLUE, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE));
		pictos.add(new Pictogram(BLUE_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_blue_down), Color.BLUE, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE));
		pictos.add(new Pictogram(BLUE_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grp), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(BLUE_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grps), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(BLUE_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_blue_single), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(GREEN_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_green_grp), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(GREEN_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_green_grps), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(GREEN_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_green_single), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(RED_DOTTED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_single), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(RED_DOTTED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_grp), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(RED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_grp), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(RED_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_red_grps), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(RED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_single), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(LINE, BitmapFactory.decodeResource(res, R.drawable.picto_line), Color.BLACK, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(POINT, BitmapFactory.decodeResource(res, R.drawable.picto_point), Color.BLACK, State.STATE_HAPPENING, Shape.CIRCLE, GraphicalOverload.NONE));
		pictos.add(new Pictogram(ZONE, BitmapFactory.decodeResource(res, R.drawable.picto_zone), Color.BLACK, State.STATE_HAPPENING, Shape.STAR_SHAPE, GraphicalOverload.NONE));
	}
	
	public static PictogramHolder getInstance(Context context) {
		if (instance == null) {
			instance = new PictogramHolder(context);
		}
		return instance;
	}

	public ArrayList<IPictogram> getPictograms() {
		return pictos;
	}
	
	public ArrayList<IPictogram> getPictograms(Shape s) {
		ArrayList<IPictogram> list = new ArrayList<IPictogram>();
		
		for (int i=0; i<pictos.size(); i++) {
			if (pictos.get(i).getShape() == s) list.add(pictos.get(i));
		}
		
		return list;
	}
	
	public ArrayList<IPictogram> getPictograms(Color c) {
		ArrayList<IPictogram> list = new ArrayList<IPictogram>();
		
		for (int i=0; i<pictos.size(); i++) {
			if (pictos.get(i).getColor() == c) list.add(pictos.get(i));
		}
		
		return list;
	}
	
	public ArrayList<IPictogram> getPictograms(State s) {
		ArrayList<IPictogram> list = new ArrayList<IPictogram>();
		
		for (int i=0; i<pictos.size(); i++) {
			if (pictos.get(i).getState() == s) list.add(pictos.get(i));
		}
		
		return list;
	}
	
	public ArrayList<IPictogram> getPictograms(GraphicalOverload go) {
		ArrayList<IPictogram> list = new ArrayList<IPictogram>();
		
		for (int i=0; i<pictos.size(); i++) {
			if (pictos.get(i).getGraphicalOverload() == go) list.add(pictos.get(i));
		}
		
		return list;
	}
	
	public String[] getPictogramNames() {
		String[] names = new String[pictos.size()];
		for (int i=0; i<pictos.size(); i++) {
			names[i] = pictos.get(i).getName();
		}
		return names;
	}
}
