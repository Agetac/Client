package org.agetac.entity;

import java.util.ArrayList;

import org.agetac.R;
import org.agetac.common.dto.ActionDTO;
import org.agetac.common.dto.ActionDTO.ActionType;
import org.agetac.common.dto.SourceDTO;
import org.agetac.common.dto.SourceDTO.SourceType;
import org.agetac.common.dto.TargetDTO;
import org.agetac.common.dto.TargetDTO.TargetType;
import org.agetac.common.dto.VehicleDemandDTO;
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

	public static final String RED_UP = "0_Incendie";
	public static final String GREEN_UP = "0_Personne";
	public static final String BLUE_UP = "0_Eau";
	public static final String ORANGE_UP = "0_Risque particulier";
	
	public static final String RED_DOWN = "1_Matérielle";
	public static final String GREEN_DOWN = "1_Personne";
	public static final String BLUE_DOWN = "1_Eau";
	public static final String ORANGE_DOWN = "1_Risque particulier";
	
	public static final String BLUE_AGRES = "2_Eau";
	public static final String BLUE_GRP = "3_Eau";
	public static final String BLUE_COL_2 = "4_Eau";
	public static final String BLUE_COL_3 = "5_Eau";
	
	public static final String ORANGE_AGRES = "2_Risques particuliers";
	public static final String ORANGE_GRP = "3_Risques particuliers";
	public static final String ORANGE_COL_2 = "4_Risques particuliers";
	public static final String ORANGE_COL_3 = "5_Risques particuliers";
	
	public static final String VIOLET_AGRES = "0_Commandement";
	public static final String VIOLET_GRP = "1_Commandement";
	public static final String VIOLET_COL_2 = "2_Commandement";
	public static final String VIOLET_COL_3 = "3_Commandement";
	
	public static final String GREEN_AGRES = "2_Personnes";
	public static final String GREEN_GRP = "3_Personnes";
	public static final String GREEN_COL_2 = "4_Personnes";
	public static final String GREEN_COL_3 = "5_Personnes";
	
	public static final String BLACK_AGRES = "0_Cheminement";
	public static final String BLACK_GRP = "1_Cheminement";
	public static final String BLACK_COL_2 = "2_Cheminement";
	public static final String BLACK_COL_3 = "3_Cheminement";
	
	public static final String RED_AGRES = "1_Incendie";
	public static final String RED_GRP = "2_Incendie";
	public static final String RED_COL_2 = "3_Incendie";
	public static final String RED_COL_3 = "4_Incendie";
	
	public static final String RED_DOTTED_AGRES = "5_Incendie";
	
	public static final String LINE_GREEN = "0_Secours personnes";
	public static final String LINE_ORANGE = "6_Risque particulier";
	public static final String LINE_RED = "0_Extinction";
	public static final String LINE_BLUE = "6_Eau";
	public static final String POINT = "_Point";
	public static final String ZONE = "_Zone";
	
	public static final String UNKNOWN = "_Inconnu ?";

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
		
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(BLUE_AGRES, BitmapFactory.decodeResource(res, R.drawable.picto_blue_agres), Color.BLUE, State.PLANNED, Shape.SQUARE, GraphicalOverload.AGRES)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(RED_AGRES, BitmapFactory.decodeResource(res, R.drawable.picto_red_agres), Color.RED, State.PLANNED, Shape.SQUARE, GraphicalOverload.AGRES)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(VIOLET_AGRES, BitmapFactory.decodeResource(res, R.drawable.picto_violet_agres), Color.VIOLET, State.PLANNED, Shape.SQUARE, GraphicalOverload.AGRES)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(BLACK_AGRES, BitmapFactory.decodeResource(res, R.drawable.picto_black_agres), Color.BLACK, State.PLANNED, Shape.SQUARE, GraphicalOverload.AGRES)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(GREEN_AGRES, BitmapFactory.decodeResource(res, R.drawable.picto_green_agres), Color.GREEN, State.PLANNED, Shape.SQUARE, GraphicalOverload.AGRES)));
		moyenEntities.add(new Entity(new VehicleDemandDTO(), new Pictogram(ORANGE_AGRES, BitmapFactory.decodeResource(res, R.drawable.picto_orange_agres), Color.ORANGE, State.PLANNED, Shape.SQUARE, GraphicalOverload.AGRES)));
		
		actionEntities.add(new Entity(new ActionDTO(ActionType.CHEM), new LinePicto(LINE_ORANGE, BitmapFactory.decodeResource(res, R.drawable.picto_line_orange), Color.ORANGE, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE, new Point(0,0), new Point(0,0), 1)));
		actionEntities.add(new Entity(new ActionDTO(ActionType.HUMAN), new LinePicto(LINE_GREEN, BitmapFactory.decodeResource(res, R.drawable.picto_line_green), Color.GREEN, State.STATE_HAPPENING, Shape.LINEAR_SHAPE, GraphicalOverload.NONE, new Point(0,0), new Point(0,0), 1)));
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
			if (entities.get(i).getPictogram().getId().equals(name))
				return entities.get(i);
		}
		return null;
	}
	
	public static IEntity getUnknownEntity() {
		return unknownEntity;
	}
}
