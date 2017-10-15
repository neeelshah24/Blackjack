package blackjack;

import java.util.*;

public class Blackjack implements BlackjackEngine {

	/**
	 * Constructor you must provide. Initializes the player's account to 200 and the
	 * initial bet to 5. Feel free to initialize any other fields. Keep in mind that
	 * the constructor does not define the deck(s) of cards.
	 * 
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	private int playerAmount;
	private int bet;
	private int numberOfDecks;
	private Random randomGenerator;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> playerCards = new ArrayList<Card>();
	private ArrayList<Card> dealerCards = new ArrayList<Card>();
	private int status = GAME_IN_PROGRESS;

	public Blackjack(Random randomGenerator, int numberOfDecks) {
		playerAmount = 200;
		bet = 5;
		this.numberOfDecks = numberOfDecks;
		this.randomGenerator = randomGenerator;
		
	}

	public int getNumberOfDecks() {
		return this.numberOfDecks;
		
	}

	public void createAndShuffleGameDeck() {
		deck = new ArrayList<Card>();
		// creates the number of cards for the given amount of decks
		for (int index = 0; index < numberOfDecks; index++) {
			for (CardSuit suit : CardSuit.values()) {
				for (CardValue value : CardValue.values()) {
					deck.add(new Card(value, suit));

				}
			}
		}
		// shuffles the deck of cards
		Collections.shuffle(deck, randomGenerator);
	}

	public Card[] getGameDeck() {
		
		Card[] cardDeck = new Card[deck.size()];
		//a loop adding Cards to the card array from the deck arraylist
		for (int index = 0; index < cardDeck.length; index++) {
			cardDeck[index] = deck.get(index);
		}
		return cardDeck;
	}

	public void deal() {
		playerCards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();
		createAndShuffleGameDeck();

		playerCards.add(deck.get(0));
		deck.remove(0);

		dealerCards.add(deck.get(0));
		dealerCards.get(0).setFaceDown();
		deck.remove(0);

		playerCards.add(deck.get(0));
		deck.remove(0);

		dealerCards.add(deck.get(0));
		deck.remove(0);

		this.status = GAME_IN_PROGRESS;
		this.playerAmount -= this.bet;
	}

	public Card[] getDealerCards() {
		Card[] dealerToArray = new Card[dealerCards.size()];
		dealerToArray = dealerCards.toArray(dealerToArray);
		return dealerToArray;
		
	}

	// this is going to return an int array filled with the values of the
	//cards that the dealer has
	public int[] getDealerCardsTotal() {
		int aceAsOneTotal = 0;
		int 	aceAsElevenTotal = 10;
		int noAce = 0;
		boolean hasAce = false;

		// check player's cards for an ace; if so boolen is changed
		for (int index = 0; index < dealerCards.size(); index++) {
			if (dealerCards.get(index).getValue() == CardValue.Ace) {
				hasAce = true;
			}
		}

		if (hasAce) {
			//if there is an ace the initial value of the ace is one
			//the for loop will add all the values of the cards to the 
			//int variable
			for (int index = 0; index < dealerCards.size(); index++) {
				aceAsOneTotal += 
						dealerCards.get(index).getValue().getIntValue();
			}
			//the value of the ace is now changed to 11
			aceAsElevenTotal += aceAsOneTotal;
			
			if (aceAsOneTotal > 21) {
				return null;
				
			} else if (aceAsOneTotal <= 21 && aceAsElevenTotal > 21) {
				int cardTotals[] = new int[1];
				cardTotals[0] = aceAsOneTotal;
				return cardTotals;
				
			} else {
				int cardTotals[] = new int[2];
				cardTotals[0] = aceAsOneTotal;
				cardTotals[1] = aceAsElevenTotal;
				return cardTotals;
				
			}
		} else {
			//no aces present; add up all the values
			for (int index = 0; index < dealerCards.size(); index++) {
				noAce += dealerCards.get(index).getValue().getIntValue();
			}
			if (noAce > 21) {
				return null;
			}
			int[] cardTotals = new int[1];
			cardTotals[0] = noAce;
			return cardTotals;
		}
	}

	// use the total from the int array in the method above to return the 
	//less than or bust
	public int getDealerCardsEvaluation() {
		int[] dealerValue = getDealerCardsTotal();

		if (dealerValue != null) {
			// if the length of the int array is two it means there is an ace
			//involved
			if (dealerValue.length == 2) {
				// blackjack is possible with only 2 cards
				if (dealerValue[1] == 21 && dealerCards.size() == 2) {
					return BLACKJACK;
					// the player can have 21 with more than 2 cards
					// but this isnt a blackJack
				} else if (dealerValue[1] == 21) {
					return HAS_21;

				} else if (dealerValue[1] < 21) {
					return LESS_THAN_21;
				}
			}
			if (dealerValue.length == 1) {

				if (dealerValue[0] == 21) {
					return HAS_21;

				} else if (dealerValue[0] < 21) {
					return LESS_THAN_21;
				}
			}

		}
		return BUST;
		

	}

	public Card[] getPlayerCards() {
		Card[] playerCardsToArray = new Card[playerCards.size()];
		playerCardsToArray = playerCards.toArray(playerCardsToArray);
		return playerCardsToArray;
		
	}

	public int[] getPlayerCardsTotal() {
			int aceAsOneTotal = 0;
			int 	aceAsElevenTotal = 10;
			int noAce = 0;
			boolean hasAce = false;

			// check player's cards for an ace; if so boolen is changed
			for (int index = 0; index < playerCards.size(); index++) {
				if (playerCards.get(index).getValue() == CardValue.Ace) {
					hasAce = true;
				}
			}

			if (hasAce) {
				//if there is an ace the initial value of the ace is one
				//the for loop will add all the values of the cards to the 
				//int variable
				for (int index = 0; index < playerCards.size(); index++) {
					aceAsOneTotal += playerCards.get(index).getValue().getIntValue();
				}
				//the value of the ace is now changed to 11
				aceAsElevenTotal += aceAsOneTotal;
				
				if (aceAsOneTotal > 21) {
					return null;
					
				} else if (aceAsOneTotal <= 21 && aceAsElevenTotal > 21) {
					int cardTotals[] = new int[1];
					cardTotals[0] = aceAsOneTotal;
					return cardTotals;
					
				} else {
					int cardTotals[] = new int[2];
					cardTotals[0] = aceAsOneTotal;
					cardTotals[1] = aceAsElevenTotal;
					return cardTotals;
					
				}
			} else {
				//no aces present; add up all the values
				for (int index = 0; index < playerCards.size(); index++) {
					noAce += playerCards.get(index).getValue().getIntValue();
				}
				if (noAce > 21) {
					return null;
				}
				int[] cardTotals = new int[1];
				cardTotals[0] = noAce;
				return cardTotals;
			}
		}

	public int getPlayerCardsEvaluation() {
		int[] playerValue = getPlayerCardsTotal();

		if (playerValue != null) {
			// if the length of the int array is two it means there is an 
			//ace involved
			if (playerValue.length == 2) {
				// blackjack is possible with only 2 cards
				if (playerValue[1] == 21 && playerCards.size() == 2) {
					return BLACKJACK;
					// the player can have 21 with more than 2 cards
					// but this isnt a blackJack
				} else if (playerValue[1] == 21) {
					return HAS_21;

				} else if (playerValue[1] < 21) {
					return LESS_THAN_21;
				}
			}
			if (playerValue.length == 1) {
				if (playerValue[0] == 21) {
					return HAS_21;

				} else if (playerValue[0] < 21) {
					return LESS_THAN_21;
				}
			}

		}
		return BUST;
	}

	public void playerHit() {
		playerCards.add(deck.get(0));
		deck.remove(0);

		if (getPlayerCardsEvaluation() == BUST) {
			this.status = DEALER_WON;
		}
	}

	/*
	 Flips the dealer's card that is currently face down and assigns cards to the 
	 dealer as long as the dealer doesn't bust and the cards have a value less than 16. 
	 Once the dealer has a hand with a value greater than or equal to 16, and less than or 
	 equal to 21, the hand will be compared against the player's hand and whoever has the 
	 hand with a highest value will win the game. If both have the same value we have a draw. 
	 The game's status will be updated to one of the following values: DEALER_WON, PLAYER_WON, 
	 or DRAW. The player's account will be updated with a value corresponding to twice the bet 
	 amount if the player wins. If there is a draw the player's account will be updated with 
	 the only the bet amount.
	 */
	
	public void playerStand() {
		//flips the dealers card that is originally upside down
		dealerCards.get(0).setFaceUp();
        int[] dealerValues;
        
        while(true) {
            dealerValues = getDealerCardsTotal();
            // value of the hand greater than or equal to 16
            if(dealerValues == null || dealerValues[dealerValues.length - 1] >= 16) {
                break;
                
            } else {
                dealerCards.add(deck.remove(0));
                
            }
        }
        int[] playerValues = getPlayerCardsTotal();
        if(dealerValues == null) {
            status = PLAYER_WON;
            
        } else if(playerValues == null) {  
            status = DEALER_WON;
          
        // if the hands are the same size and the value of the dealer is greater
        // the dealer wins
        } else if(dealerValues.length == 2 && playerValues.length == 2) {
            if(dealerValues[1] > playerValues[1]) {
                status = DEALER_WON;
             // player wins with larger values   
            } else if(dealerValues[1] < playerValues[1]) {
                status = PLAYER_WON;
                playerAmount += (getBetAmount() * 2);
                
            } else {
                status = DRAW;
                playerAmount += getBetAmount();
                
            }
            
            // the size is the same compare the only card
        } else if(dealerValues.length == 1 && playerValues.length == 1) {
        		if(dealerValues[0] > playerValues[0]) {
                status = DEALER_WON;
                
            } else if(dealerValues[0] < playerValues[0]) {
                status = PLAYER_WON;
                playerAmount += (getBetAmount() * 2);
                
            } else {
                status = DRAW;
                playerAmount += getBetAmount();
                
            }
            //player size more than dealer size, compare player first card
        } else if(dealerValues.length == 1 && playerValues.length == 2) {
        	//dealer wins with the larger value
            if(dealerValues[0] > playerValues[1]) {
                status = DEALER_WON;
                
            } else if(dealerValues[0] < playerValues[1]) {
                status = PLAYER_WON;
                playerAmount += (getBetAmount() * 2);
                
            } else {
                status = DRAW;
                playerAmount += getBetAmount();
                
            }
            // if the dealer has more cards, compare the first values of the 
            // deck
        } else if(dealerValues.length == 2 && playerValues.length == 1) {
          //dealer value more than player value, dealer wins
            if(dealerValues[1] > playerValues[0]) {
                status = DEALER_WON;
             // player wins with a greater value   
            } else if(dealerValues[1] < playerValues[0]) {
                status = PLAYER_WON;
                playerAmount += (getBetAmount() * 2);
                
            } else {
                status = DRAW;
                playerAmount += getBetAmount();
                
            }
        }
	}
		  

	public int getGameStatus() {
		return this.status;
	}

	public void setBetAmount(int amount) {
		bet = amount;
	}

	public int getBetAmount() {
		return this.bet;
	}

	public void setAccountAmount(int amount) {
		playerAmount = amount;
	}

	public int getAccountAmount() {
		return this.playerAmount;
		
	}

	/* Feel Free to add any private methods you might need */

}