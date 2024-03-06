import java.util.ArrayList;
import java.util.Collections;

public class Minimax {

	private int bestMove = 0;
	
	public int getBestMove(Marker[][] markers, int requester) {
		bestMove = 0;
		minimax(markers, requester, true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

		return bestMove;
	}

	private int minimax(Marker[][] markers, int requester, boolean requesterMove, int depth,
			int alpha, int beta) {


		//checking for recursion exit condition
		int winner = Checker.getWinType(markers);
		if(winner >= 0 || getMarkersPlacedSize(markers) == Main.SIZE) {
			return getFieldScore(markers, requester, depth);
		}
		
		ArrayList<Integer> scores = new ArrayList<>();
		int[] openMoves = getOpenSpotsIndexes(markers);
		int score = 0;

		//iterating through the open moves left and counting scores for each
		for (int openMove : openMoves) {
			int x = openMove % Main.ROWS;
			int y = openMove / Main.ROWS;

			if (!requesterMove) {
				depth++;
			}

			int marker = requesterMove ? requester : requester + 1;
			markers[x][y] = new Marker(marker);
			score = minimax(markers, requester, !requesterMove, depth, alpha, beta);
			//adding all the scores for the moves to an array
			scores.add(score);
			markers[x][y] = null;

			//pruning using "alpha-beta", skip checking moves that cannot affect the outcome
					if (requesterMove) {
						int maxValue = Math.max(Integer.MIN_VALUE, score);
						alpha = Math.max(alpha, maxValue);
						if (alpha > beta) {
							return maxValue;
						}
					} else {
						int minValue = Math.min(Integer.MAX_VALUE, score);
						beta = Math.min(beta, minValue);
						if (beta < alpha) {
							return minValue;
				}
			}
		}
		int scoreIndex = 0;
		if(requesterMove) {//getting the maximum score (best move) for the AI
			scoreIndex = scores.indexOf(Collections.max(scores));
		} else {// getting the lowest score for the opponent
			scoreIndex = scores.indexOf(Collections.min(scores));

		}
		//constantly updating the best move index
		bestMove = openMoves[scoreIndex];
		
		return scores.get(scoreIndex);
	}

	//calculating scores : size - depth for maximizer, -size + depth for minimizer
	private int getFieldScore(Marker[][] markers, int requester, int depth) {
		ArrayList<Marker> match = Checker.checkWin(markers);
		if(match == null) {
			return 0;
		}

		if(match.get(0).getType() == requester) {
			return Main.SIZE - depth;
		}
		
		return (Main.SIZE * -1) + depth;
	}

	//getting an array with the free spots (no markers)
	private int[] getOpenSpotsIndexes(Marker[][] markers) {
		int[] openSpots = new int[Main.SIZE - getMarkersPlacedSize(markers)];
		int openSpotIndex = 0;
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers[x].length; y++) {
				if(markers[x][y] == null) {
					openSpots[openSpotIndex] = (y * Main.ROWS) + x;
					openSpotIndex++;
				}
			}
		}
		
		return openSpots;
	}

	private int getMarkersPlacedSize(Marker[][] markers) {
		int result = 0;
		for (Marker[] marker : markers) {
			for (int y = 0; y < marker.length; y++) {
				if (marker[y] != null) {
					result++;
				}
			}
		}
		return result;
	}
	
}
