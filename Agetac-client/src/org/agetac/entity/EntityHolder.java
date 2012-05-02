package org.agetac.entity;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.GroupDTO;
import org.agetac.common.dto.IModel;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.ActionDTO.ActionType;
import org.agetac.common.dto.SourceDTO.SourceType;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.TargetDTO.TargetType;
import org.agetac.common.dto.VehicleDTO;
import org.agetac.common.dto.VehicleDemandDTO;
import org.agetac.entity.Entity.EntityState;
import org.agetac.view.Color;
import org.agetac.view.GraphicalOverload;
import org.agetac.view.LinePicto;
import org.agetac.view.Pictogram;
import org.agetac.view.Shape;
import org.agetac.view.State;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;

/**
 * Crée les entitées liées aux pictogrames et aux models.
 * Par défaut, tous les models sont vides.
 * @author leiko
 *
 */
public class EntityHolder {

	public static final String RED_UP = "Source de feu";
	public static final String RED_DOWN = "red_down";
	public static final String GREEN_UP = "green_up";
	public static final String GREEN_DOWN = "green_down";
	public static final String BLUE_UP = "Source d'eau";
	public static final String BLUE_DOWN = "blue_down";
	public static final String ORANGE_UP = "Produits chimiques";
	public static final String ORANGE_DOWN = "orange_down";
	
	public static final String BLUE_NONE = "blue_none";
	public static final String BLUE_GRP = "blue_grp";
	public static final String BLUE_COL = "blue_col";
	public static final String BLUE_ISOLE = "blue_isole";
	
	public static final String GREEN_NONE = "green_none";
	public static final String GREEN_GRP = "green_grp";
	public static final String GREEN_COL = "green_col";
	public static final String GREEN_ISOLE = "green_isole";
	
	public static final String RED_DOTTED_SINGLE = "red_dotted_single";
	public static final String RED_DOTTED_NONE = "red_dotted_none";
	
	public static final String RED_NONE = "red_none";
	public static final String RED_GRP = "red_grp";
	public static final String RED_COL = "red_col";
	public static final String RED_ISOLE = "red_isole";
	
	public static final String LINE_BLACK = "Commandement";
	public static final String LINE_RED = "Action feu";
	public static final String LINE_BLUE = "Action eau";
	public static final String POINT = "Point";
	public static final String ZONE = "Zone";
	
	public static final String UNKNOWN = "Inconnu ?";

	private static EntityHolder instance;
	private Resources res;
	private static ArrayList<IEntity> entities;
	private static ArrayList<IEntity> targetEntities;
	private static ArrayList<IEntity> sourceEntities;
	private static ArrayList<IEntity> moyenEntities;
	private static ArrayList<IEntity> actionEntities;
	private static IEntity unknownEntity;

	private EntityHolder(Context context) {
		this.res = context.getResources();
		targetEntities = new ArrayList<IEntity>();
		actionEntities = new ArrayList<IEntity>();
		sourceEntities = new ArrayList<IEntity>();
		moyenEntities = new ArrayList<IEntity>();
		
		Options options = new Options();
		options.inPurgeable = false;
		
		sourceEntities.add(new Entity(new SourceDTO(SourceType.FIRE), new Pictogram(RED_UP, BitmapFactory.decodeResource(res, R.drawable.picto_red_up, options), Color.RED, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE)));
		targetEntities.add(new Entity(new TargetDTO(TargetType.FIRE), new Pictogram(RED_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_red_down, options), Color.RED, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE)));
		targetEntities.add(new Entity(new TargetDTO(TargetType.HUMAN), new Pictogram(GREEN_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_green_down), Color.GREEN, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE)));
		sourceEntities.add(new Entity(new SourceDTO(SourceType.CHEM), new Pictogram(ORANGE_UP, BitmapFactory.decodeResource(res, R.drawable.picto_orange_up), Color.ORANGE, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE)));
		targetEntities.add(new Entity(new TargetDTO(TargetType.CHEM), new Pictogram(ORANGE_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_orange_down), Color.ORANGE, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE)));
		sourceEntities.add(new Entity(new SourceDTO(SourceType.WATER), new Pictogram(BLUE_UP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_up), Color.BLUE, State.STATE_HAPPENING, Shape.TRIANGLE_UP, GraphicalOverload.NONE)));
		targetEntities.add(new Entity(new TargetDTO(TargetType.WATER), new Pictogram(BLUE_DOWN, BitmapFactory.decodeResource(res, R.drawable.picto_blue_down), Color.BLUE, State.STATE_HAPPENING, Shape.TRIANGLE_DOWN, GraphicalOverload.NONE)));
		
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(BLUE_NONE, BitmapFactory.decodeResource(res, R.drawable.picto_blue_none), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(BLUE_ISOLE, BitmapFactory.decodeResource(res, R.drawable.picto_blue_isole), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.ISOLE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(BLUE_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_blue_grp), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.GROUPE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(BLUE_COL, BitmapFactory.decodeResource(res, R.drawable.picto_blue_col), Color.BLUE, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.COLONNE)));
	
		
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(GREEN_NONE, BitmapFactory.decodeResource(res, R.drawable.picto_green_none), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(GREEN_ISOLE, BitmapFactory.decodeResource(res, R.drawable.picto_green_isole), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.ISOLE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(GREEN_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_green_grp), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.GROUPE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(GREEN_COL, BitmapFactory.decodeResource(res, R.drawable.picto_green_col), Color.GREEN, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.COLONNE)));
		
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(RED_DOTTED_SINGLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_isole), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.ISOLE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(RED_DOTTED_NONE, BitmapFactory.decodeResource(res, R.drawable.picto_red_dotted_none), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE)));
		
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(RED_NONE, BitmapFactory.decodeResource(res, R.drawable.picto_red_none), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.NONE)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(RED_ISOLE, BitmapFactory.decodeResource(res, R.drawable.picto_red_isole), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.ISOLE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(RED_GRP, BitmapFactory.decodeResource(res, R.drawable.picto_red_grp), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.GROUPE)));
//		moyenEntities.add(new Entity(new GroupDTO(), new Pictogram(RED_COL, BitmapFactory.decodeResource(res, R.drawable.picto_red_col), Color.RED, State.STATE_HAPPENING, Shape.SQUARE, GraphicalOverload.COLONNE)));
		
		actionEntities.add(new Entity(new ActionDTO(ActionType.HUMAN), new LinePicto(LINE_BLACK, BitmapFactory.decodeResource(res, R.drawable.picto_line_black), Color.BLACK, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE, new Point(0,0), new Point(0,0), 1)));
		actionEntities.add(new Entity(new ActionDTO(ActionType.FIRE), new LinePicto(LINE_RED, BitmapFactory.decodeResource(res, R.drawable.picto_line_red), Color.RED, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE, new Point(0,0), new Point(0,0), 1)));
		actionEntities.add(new Entity(new ActionDTO(ActionType.WATER), new LinePicto(LINE_BLUE, BitmapFactory.decodeResource(res, R.drawable.picto_line_blue), Color.BLUE, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE, new Point(0,0), new Point(0,0), 1)));
		actionEntities.add(new Entity(new SourceDTO(), new Pictogram(POINT, BitmapFactory.decodeResource(res, R.drawable.picto_point), Color.BLACK, State.STATE_HAPPENING, Shape.CIRCLE, GraphicalOverload.NONE)));
		actionEntities.add(new Entity(new SourceDTO(), new Pictogram(ZONE, BitmapFactory.decodeResource(res, R.drawable.picto_zone), Color.BLACK, State.STATE_HAPPENING, Shape.STAR_SHAPE, GraphicalOverload.NONE)));
		
		unknownEntity = new Entity(new SourceDTO(), new Pictogram(UNKNOWN, BitmapFactory.decodeResource(res, R.drawable.picto_unknown), Color.BLACK, State.STATE_HAPPENING, Shape.CIRCLE, GraphicalOverload.NONE));
		
		entities = new ArrayList<IEntity>();
		entities.addAll(sourceEntities);
		entities.addAll(targetEntities);
		entities.addAll(actionEntities);
		entities.addAll(moyenEntities);
	}

	public static EntityHolder getInstance(Context context) {
		if (instance == null) {
			instance = new EntityHolder(context);
		}
		return instance;
	}

	public static ArrayList<IEntity> getEntities() {
		return entities;
	}

	public ArrayList<IEntity> getActionEntities() {
		return actionEntities;
	}
	
	public ArrayList<IEntity> getTargetEntities() {
		return targetEntities;
	}
	
	public ArrayList<IEntity> getSourceEntities() {
		return sourceEntities;
	}
	
	public ArrayList<IEntity> getMoyenEntities() {
		return moyenEntities;
	}

	public String[] getEntitiesName() {
		ArrayList<IEntity> entities = EntityHolder.getEntities();
		
		String[] names = new String[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			names[i] = entities.get(i).getPictogram().getName();
		}
		return names;
	}

	public static IEntity getEntity(String name) {
		ArrayList<IEntity> entities = EntityHolder.getEntities();
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getPictogram().getName().equals(name))
				return entities.get(i);
		}
		return null;
	}
	
	public static IEntity getUnknownEntity() {
		return unknownEntity;
	}
}
