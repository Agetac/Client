package org.agetac.view;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.common.model.impl.Action;
import org.agetac.common.model.impl.Cible;
import org.agetac.common.model.impl.DemandeMoyen;
import org.agetac.common.model.impl.Groupe;
import org.agetac.common.model.impl.Source;
import org.agetac.entity.Entity;
import org.agetac.entity.Entity.EntityState;
import org.agetac.entity.IEntity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class EntityHolder {

	public static final String RED_UP = "Source de feu";
	public static final String RED_DOWN = "red_down";
	public static final String GREEN_UP = "Produits chimiques";
	public static final String GREEN_DOWN = "green_down";
	public static final String BLUE_UP = "Source d'eau";
	public static final String BLUE_DOWN = "blue_down";
	public static final String ORANGE_UP = "orange_up";
	public static final String ORANGE_DOWN = "orange_down";
	public static final String BLUE_GRP = "blue_grp";
	public static final String BLUE_GRPS = "blue_grps";
	public static final String BLUE_SINGLE = "blue_single";
	public static final String GREEN_GRP = "green_grp";
	public static final String GREEN_GRPS = "green_grps";
	public static final String GREEN_SINGLE = "green_single";
	public static final String RED_DOTTED_SINGLE = "red_dotted_single";
	public static final String RED_DOTTED_GRP = "red_dotted_grp";
	public static final String RED_GRP = "red_grp";
	public static final String RED_GRPS = "red_grps";
	public static final String RED_SINGLE = "red_single";
	public static final String LINE = "Ligne";
	public static final String POINT = "Point";
	public static final String ZONE = "Zone";

	private static EntityHolder instance;
	private Resources res;
	private ArrayList<IEntity> entities;

	private EntityHolder(Context context) {
		this.res = context.getResources();
		entities = new ArrayList<IEntity>();
		
		Options options = new Options();
		options.inPurgeable = false;
		
		entities.add(new Entity(new Source(), new Pictogram(RED_UP, BitmapFactory.decodeResource(res, R.drawable.picto_red_up, options), Color.RED, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Cible(), new Pictogram(RED_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_red_down, options), Color.RED, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Source(), new Pictogram(GREEN_UP, BitmapFactory.decodeResource(res, R.drawable.picto_green_up), Color.GREEN, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Cible(), new Pictogram(GREEN_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_green_down), Color.GREEN, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Source(), new Pictogram(ORANGE_UP, BitmapFactory.decodeResource(res, R.drawable.picto_orange_up), Color.ORANGE, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Cible(), new Pictogram(ORANGE_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_orange_down), Color.ORANGE, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Source(), new Pictogram(BLUE_UP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_up), Color.BLUE, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Cible(), new Pictogram(BLUE_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_blue_down), Color.BLUE, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(BLUE_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grp), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(BLUE_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grps), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new DemandeMoyen(), new Pictogram(BLUE_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_blue_single), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(GREEN_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_green_grp), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(GREEN_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_green_grps), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new DemandeMoyen(), new Pictogram(GREEN_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_green_single), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new DemandeMoyen(), new Pictogram(RED_DOTTED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_single), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(RED_DOTTED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_grp), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(RED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_grp), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Groupe(), new Pictogram(RED_GRPS, BitmapFactory.decodeResource(res, R.drawable.picto_red_grps), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new DemandeMoyen(), new Pictogram(RED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_single), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Action(), new LinePicto(LINE, BitmapFactory.decodeResource(res, R.drawable.picto_line), Color.BLACK, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE, null, null, 1), EntityState.OFF_SITAC));
		entities.add(new Entity(new Source(), new Pictogram(POINT, BitmapFactory.decodeResource(res, R.drawable.picto_point), Color.BLACK, State.STATE_HAPPENING, Shape.CIRCLE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
		entities.add(new Entity(new Source(), new Pictogram(ZONE, BitmapFactory.decodeResource(res, R.drawable.picto_zone), Color.BLACK, State.STATE_HAPPENING, Shape.STAR_SHAPE, GraphicalOverload.NONE), EntityState.OFF_SITAC));
	}

	public static EntityHolder getInstance(Context context) {
		if (instance == null) {
			instance = new EntityHolder(context);
		}
		return instance;
	}

	public ArrayList<IEntity> getEntities() {
		return entities;
	}

	public ArrayList<IEntity> getEntities(Shape s) {
		ArrayList<IEntity> list = new ArrayList<IEntity>();

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPictogram().getShape() == s)
				list.add(entities.get(i));
		}

		return list;
	}

	public ArrayList<IEntity> getEntities(Color c) {
		ArrayList<IEntity> list = new ArrayList<IEntity>();

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPictogram().getColor() == c)
				list.add(entities.get(i));
		}

		return list;
	}

	public ArrayList<IEntity> getEntities(State s) {
		ArrayList<IEntity> list = new ArrayList<IEntity>();

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPictogram().getState() == s)
				list.add(entities.get(i));
		}

		return list;
	}

	public ArrayList<IEntity> getEntities(GraphicalOverload go) {
		ArrayList<IEntity> list = new ArrayList<IEntity>();

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPictogram().getGraphicalOverload() == go)
				list.add(entities.get(i));
		}

		return list;
	}

	public String[] getEntitiesName() {
		String[] names = new String[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			names[i] = entities.get(i).getPictogram().getName();
		}
		return names;
	}

	public IEntity getEntity(String name) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPictogram().getName().equals(name))
				return entities.get(i);
		}
		return null;
	}
}
