package tp.client.game;

import java.util.*;
import tp.client.structural.*;

public class MoveValidator {
    private static int getCityDistance(int[] coords1, int[] coords2){
        return (Math.abs(coords1[0] - coords2[0]) + 
                Math.abs(coords1[1] - coords2[1]) + 
                Math.abs(coords1[2] - coords2[2]))/2;
    }

    private static boolean inLine(int[] coords1, int[] coords2){
        return coords1[0] == coords2[0] || coords1[1] == coords2[1] || coords1[2] == coords2[2];
    }

    public static boolean validate(List<Step> move, Pawn[] initState){
        int moveType = 0;

        Pawn[] tempState = new Pawn[initState.length];
        for (int i = 0; i < initState.length; i++){
            tempState[i] = initState[i].clone();
        }

        Pawn moved = null;

        for (Step subMove : move){
            for (Pawn occupant : tempState){
                if (occupant.location == subMove.destination){
                    return false; //destination occupied
                }
            }

            Pawn realactor = null;
            for (Pawn piece : tempState){
                if (subMove.actor.id == piece.id){
                    if (moved == null){
                        realactor = piece;
                        moved = piece;
                        break;
                    }
                    else{
                        if (moved != piece){
                            return false; //Moving different pieces in one move
                        }
                        realactor = piece;
                        break;
                    }
                }
            }
            if (realactor == null){
                return false; //Piece no longer exists
            }

            if (realactor.playerid == realactor.location.playerGoal && realactor.playerid != subMove.destination.playerGoal ){
                return false; //Leaving goal
            }

            if (getCityDistance(realactor.location.coordinates, subMove.destination.coordinates) == 1){
                if (moveType != 0){
                    return false; //Not first step
                }
                else{
                    moveType = 1;
                    realactor.location = subMove.destination;
                }
            }
            else if (getCityDistance(realactor.location.coordinates, subMove.destination.coordinates) == 2 ){
                if (moveType != 1){
                    if (inLine(realactor.location.coordinates, subMove.destination.coordinates)){
                        int[] jumpedCoords = new int[3];
                        jumpedCoords[0] = (realactor.location.coordinates[0] + subMove.destination.coordinates[0])/2;
                        jumpedCoords[1] = (realactor.location.coordinates[1] + subMove.destination.coordinates[1])/2;
                        jumpedCoords[2] = (realactor.location.coordinates[2] + subMove.destination.coordinates[2])/2;

                        Pawn jumped = null;
                        for (Pawn target: tempState){
                            if (target.location.coordinates[0] == jumpedCoords[0] &&
                                target.location.coordinates[1] == jumpedCoords[1] &&
                                target.location.coordinates[2] == jumpedCoords[2]){
                                jumped = target;
                            }
                        }
                        if (jumped == null){
                            return false; //Jump over air
                        }
                        else{
                            moveType = 2;
                            realactor.location = subMove.destination;
                        }
                    }
                    else{
                        return false; //Jump not in line
                    }
                }
                else {
                    return false; //Jump after move
                }
            }
            else{
                return false; //Too long step
            }

        }

        return true;

    }
}
