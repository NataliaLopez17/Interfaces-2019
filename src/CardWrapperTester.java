import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


/**
 * Assumptions:
 * - These method from CardDeck are correct:
 * 		- CardDeck(int size)
 * 		- CardDeck(Card[] deck)
 * 		- add(Card c)
 * 		- equals(CardDeck cd)
 * 		- getCard(int index)
 * 		- getDeck()
 * 		- getCardCount()
 * - These methods from Card are correct:
 * 		- clone()
 * 		- equals(Card c)
 */

public class CardWrapperTester {

	CardWrapper.CardDeck cdEmpty;
	CardWrapper.CardDeck cdHalfFull;
	CardWrapper.CardDeck cdFull;

	CardWrapper.Card c1;
	CardWrapper.Card c2;
	CardWrapper.Card c3;
	CardWrapper.Card c4;
	CardWrapper.Card c5;
	CardWrapper.Card c6;
	CardWrapper.Card c1clone;

	
	@Before
	public void setUp() {
		c1 = new CardWrapper.Card(CardWrapper.Suit.HEARTS, CardWrapper.Rank.TEN);
		c2 = new CardWrapper.Card(CardWrapper.Suit.CLUBS, CardWrapper.Rank.A);
		c3 = new CardWrapper.Card(CardWrapper.Suit.DIAMONDS, CardWrapper.Rank.K);
		
		
		c4 = new CardWrapper.Card(
				CardWrapper.Suit.HEARTS, CardWrapper.Rank.EIGHTH);
		c5 = new CardWrapper.Card(
				CardWrapper.Suit.SPADES, CardWrapper.Rank.TWO);
		c6 = new CardWrapper.Card(
				CardWrapper.Suit.CLUBS, CardWrapper.Rank.TEN);
		c1clone = c1.clone();

		CardWrapper.Card[] deck1 = {c1, c2, c3};

		cdEmpty = new CardWrapper.CardDeck(0); 
		cdHalfFull = new CardWrapper.CardDeck(deck1);
		cdHalfFull.add(c4);
		cdFull = new CardWrapper.CardDeck(deck1); 


	}

	@Test
	public void testClone() {
		// Check interface
		assertTrue("CardDeck should implement interface Cloneable",
				cdEmpty instanceof Cloneable);

		// Get Deck
		CardWrapper.Card[] fullDeck = cdFull.getDeck();

		// Clone Collections
		CardWrapper.CardDeck cdEmptyClone= cdEmpty.clone();
		CardWrapper.CardDeck cdHalfFullClone= cdHalfFull.clone();
		CardWrapper.CardDeck cdFullClone= cdFull.clone();

		// Check Object Uniqueness
		assertFalse("Clone should return a new instance of CardDeck",
				cdFull == cdFullClone);		
		assertFalse("Clone should return a new instance of CardDeck",
				cdEmpty == cdEmptyClone);

		// Check If Objects are Equal
		assertEquals("The clone should have the same attributes as the original",
				cdEmpty, cdEmptyClone);		
		assertEquals("The clone should have the same attributes as the original",
				cdFull, cdFullClone);
		assertEquals("The clone should have the same attributes as the original",
				cdHalfFull, cdHalfFullClone);

		// Check if inner array is not changed
		CardWrapper.Card[] fullDeckAfterClone = cdFull.getDeck();

		assertTrue("The original card deck must not change",
				Arrays.equals(fullDeck, fullDeckAfterClone));

		// Checks if the card count was updated.
		assertEquals("The card count is not being updated", cdHalfFull.getCardCount(), cdHalfFullClone.getCardCount());
		
		// Check if card members are also unique
		assertFalse("Card members must also be cloned", 
				cdFull.getCard(0) == cdFullClone.getCard(0));
		assertFalse("Card members must also be cloned", 
				cdHalfFull.getCard(0) == cdHalfFullClone.getCard(0));
		assertFalse("Card members must also be cloned", 
				cdFull.getCard(2) == cdFullClone.getCard(2));
	}

	@Test
	public void testIsEmpty() {
		assertTrue("Class must implement the Collection interface", 
				cdEmpty instanceof Collection);
		assertTrue("This deck is empty", cdEmpty.isEmpty());
		assertFalse("This deck not is empty", cdHalfFull.isEmpty());
		assertFalse("This deck not is empty", cdFull.isEmpty());
	}

	@Test
	public void testSize() {
		assertTrue("Class must implement the Collection interface", 
				cdEmpty instanceof Collection);
		assertEquals("This deck is empty", 0,cdEmpty.size());
		assertEquals("This deck has 4 items", 4,cdHalfFull.size());
		assertEquals("This deck has 3 items", 3,cdFull.size());
	}

	@Test
	public void testContains() {
		assertTrue("Class must implement the Collection interface", 
				cdEmpty instanceof Collection);
		assertFalse("This doesnt contain this card", cdEmpty.contains(c1));
		assertFalse("This doesnt contain this card", cdHalfFull.contains(c5));
		assertTrue("This does contain this card", cdHalfFull.contains(c1));
		assertTrue("This does contain this card", cdHalfFull.contains(c4));
		assertTrue("This does contain this card", cdFull.contains(c3));
	}

	@Test
	public void testCompareTo() {
		
		assertTrue("The cards should implement the Comparable interface.", c1 instanceof Comparable);
		
		assertTrue("The first card should be greater than the second card", c1.compareTo(c4) > 0);
		assertTrue("The first card should be lesser than the second card", c4.compareTo(c1) < 0);
		
		assertTrue("The first card should be greater than the second card", c1.compareTo(c6) > 0);
		assertTrue("The first card should be lesser than the second card", c6.compareTo(c1) < 0);	
		
		assertTrue("The first card should be greater than the second card", c5.compareTo(c6) < 0);
		assertTrue("The first card should be lesser than the second card", c6.compareTo(c5) > 0);
		
		assertTrue("The first card should be equal to the second card", c1.compareTo(c1) == 0);		
		assertTrue("The first card should be equal to the second card", c1.compareTo(c1clone) == 0);	
	}

	@Test
	public void testIterable() {

		//Initially verifies that CardDeck implements Iterable interface to avoid further exceptions
		assertTrue("CardDeck should implement Iterable interface", cdHalfFull instanceof Iterable);
		assertTrue("CardDeck should implement Iterable interface", cdEmpty instanceof Iterable);


		Iterator<CardWrapper.Card> iter = cdHalfFull.iterator();
		Iterator<CardWrapper.Card> iter2 = cdEmpty.iterator();

		//Verifies that the iterators implements the Iterator interface and are of class DeckIterator
		assertTrue("iter should implement Iterator interface", iter instanceof Iterator);
		assertTrue("iter2 should implement Iterator interface", iter2 instanceof Iterator);	
		assertTrue("iter should be DeckIterable class", iter instanceof CardWrapper.DeckIterator);
		assertTrue("iter2 should be DeckIterable class", iter2 instanceof CardWrapper.DeckIterator);
	}

	@Test 
	public void testDealCard() {
		
		assertTrue("The class must implement the Dealable interface", cdHalfFull instanceof CardWrapper.Dealable);
		
		CardWrapper.Card[] fullArray = cdFull.getDeck();
		int cardCount = cdFull.getCardCount();
		CardWrapper.Card halfFullDealtCard1 = ((CardWrapper.Dealable)cdHalfFull).dealCard();
		CardWrapper.Card halfFullDealtCard2 = ((CardWrapper.Dealable)cdHalfFull).dealCard();
		CardWrapper.Card fullDealtCard = ((CardWrapper.Dealable)cdFull).dealCard();
		CardWrapper.Card[] fullArrayAfter = cdFull.getDeck();

				
		// Verify that the dealt card is the first card in original deck
		
		assertEquals("Card with invalid suit returned by DealCard", c1.getSuit(), halfFullDealtCard1.getSuit());
		assertEquals("Card with invalid rank returned by DealCard", c1.getRank(), halfFullDealtCard1.getRank());
		
		assertEquals("Card with invalid suit returned by DealCard", c2.getSuit(), halfFullDealtCard2.getSuit());
		assertEquals("Card with invalid rank returned by DealCard", c2.getRank(), halfFullDealtCard2.getRank());
		
		assertEquals("Card with invalid suit returned by DealCard", c1.getSuit(), fullDealtCard.getSuit());
		assertEquals("Card with invalid rank returned by DealCard", c1.getRank(), fullDealtCard.getRank());
		
		// verify that deck has one less card
		
		assertTrue("The array should be the same instance.", fullArray == fullArrayAfter);
		assertEquals("The card count should be updated.", cardCount-1, cdFull.getCardCount());
		
		// verify that the removed card is not somewhere else in the deck
		for(int i = 0; i < fullArrayAfter.length; i++) {
			assertNotEquals("Card wasn't removed succesfully from the deck", c1 ,fullArrayAfter[i]);
		}
		
		// verify the deck is in the expected state after removing a card.
		assertEquals("The array should stay in the original order.", c2, fullArrayAfter[0]);
		assertEquals("The array should stay in the original order.", c3, fullArrayAfter[1]);
		assertTrue("The removal of the card should leave a null it its place.", null==fullArrayAfter[2]);
	}
	
	@Test
	public void testShuffleDeck() {
		
		assertTrue("The class must implement the Dealable interface", cdHalfFull instanceof CardWrapper.Dealable);

		CardWrapper.Card[] customDeck1 = { c1, c2, c3 ,c4, c5, c6, c1clone };
		CardWrapper.Dealable dealCustom = new CardWrapper.CardDeck(customDeck1);
		CardWrapper.Dealable dealCustomCopy = new CardWrapper.CardDeck(customDeck1);
		
		dealCustom.shuffleDeck();
		dealCustomCopy.shuffleDeck();
		cdHalfFull.shuffleDeck();
		
		CardWrapper.CardDeck cdCustom = (CardWrapper.CardDeck) dealCustom;
		CardWrapper.CardDeck cdCustomCopy = (CardWrapper.CardDeck) dealCustomCopy;
		
		// Make sure its not the original combination.
		assertFalse("Same combination returned.", 
				c1.equals(cdCustom.getCard(0)) && c2.equals(cdCustom.getCard(1))
						&& c3.equals(cdCustom.getCard(2)) && c4.equals(cdCustom.getCard(3))
						&& c5.equals(cdCustom.getCard(4)) && c6.equals(cdCustom.getCard(5))
						&& c1clone.equals(cdCustom.getCard(6)));
		
		// Make sure its not a constant shuffle.
		assertTrue("Its very unlikely to get the same combination after one call", Arrays.equals(cdCustom.getDeck(), cdCustomCopy.getDeck()));
		
		// Make sure no cards where lost 
		
		assertTrue("The card c1 was lost.", 
				c1.equals(cdCustom.getCard(0)) || c1.equals(cdCustom.getCard(1))
						|| c1.equals(cdCustom.getCard(2)) || c1.equals(cdCustom.getCard(3))
						|| c1.equals(cdCustom.getCard(4)) || c1.equals(cdCustom.getCard(5))
						||c1.equals(cdCustom.getCard(6)));
		
		assertTrue("The card c2 was lost.", 
				c2.equals(cdCustom.getCard(0)) || c2.equals(cdCustom.getCard(1))
						|| c2.equals(cdCustom.getCard(2)) || c2.equals(cdCustom.getCard(3))
						|| c2.equals(cdCustom.getCard(4)) || c2.equals(cdCustom.getCard(5))
						||c2.equals(cdCustom.getCard(6)));
		
		assertTrue("The card c3 was lost.", 
				c3.equals(cdCustom.getCard(0)) || c3.equals(cdCustom.getCard(1))
						|| c3.equals(cdCustom.getCard(2)) || c3.equals(cdCustom.getCard(3))
						|| c3.equals(cdCustom.getCard(4)) || c3.equals(cdCustom.getCard(5))
						||c3.equals(cdCustom.getCard(6)));
		
		assertTrue("The card c4 was lost.", 
				c4.equals(cdCustom.getCard(0)) || c4.equals(cdCustom.getCard(1))
						|| c4.equals(cdCustom.getCard(2)) || c4.equals(cdCustom.getCard(3))
						|| c4.equals(cdCustom.getCard(4)) || c4.equals(cdCustom.getCard(5))
						||c4.equals(cdCustom.getCard(6)));
		
		assertTrue("The card c5 was lost.", 
				c5.equals(cdCustom.getCard(0)) || c5.equals(cdCustom.getCard(1))
						|| c5.equals(cdCustom.getCard(2)) || c5.equals(cdCustom.getCard(3))
						|| c5.equals(cdCustom.getCard(4)) || c5.equals(cdCustom.getCard(5))
						||c5.equals(cdCustom.getCard(6)));
		
		assertTrue("The card c6 was lost.", 
				c6.equals(cdCustom.getCard(0)) || c6.equals(cdCustom.getCard(1))
						|| c6.equals(cdCustom.getCard(2)) || c6.equals(cdCustom.getCard(3))
						|| c6.equals(cdCustom.getCard(4)) || c6.equals(cdCustom.getCard(5))
						||c6.equals(cdCustom.getCard(6)));

		// Checks that the nulls are not included in the randomization.
		for(int i = 0; i < cdHalfFull.getCardCount(); i++) {
			assertFalse("A null should not be found here.", null == cdHalfFull.getCard(i));
		}
	}
}
