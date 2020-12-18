package com.equinix.appops.dart.portal.common;

import java.math.BigInteger;
import java.text.Collator;
import java.util.Comparator;
import java.util.logging.Logger;

import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.DependentAttrUpdate;

/**
 * Comparator for ordering by Collator while treating digits numerically.
 * This provides a "natural" order that humans usually perceive as 'logical'.
 * 
 * It should work reasonably well for western languages (provided you
 * use the proper collator when constructing). For free control over the
 * Collator, use the constructor that takes a Collator as parameter.
 * Configure the Collator using Collator.setDecomposition()/setStrength()
 * to suit your requirements.
 */
public class CGAlphaNumComparator implements Comparator<Object> {
	
	private static final Logger log = Logger.getLogger("cGAlphaNumComparatorLogger");

	
    /**
     * The collator used for comparison of the alpha part
     */
    @SuppressWarnings("unused")
	private final Collator collator;

    /**
     * Create comparator using platform default collator.
     * (equivalent to using Collator.getInstance())
     */
    public CGAlphaNumComparator() {
        this(Collator.getInstance()); 
    }

    /**
     * Create comparator using specified collator
     */
    public CGAlphaNumComparator(final Collator collator) {
        if (collator == null)
        {
        	throw new IllegalArgumentException("collator must not be null");
        }
        this.collator = collator;
    }

    /**
     * Ideally this would be generalized to Character.isDigit(), but I have
     * no knowledge about arabic language and other digits, so I treat
     * them as characters...
     */
    private static boolean isDigit(final int character) {
        // code between ASCII '0' and '9'?
        return character >= 48 && character <= 57;
    }

    /**
     * Get subsequence of only characters or only digits, but not mixed
     */
    @SuppressWarnings("unused")
	private static CharSequence getChunk(final CharSequence charSeq, final int start) {
        int index = start;
        final int length = charSeq.length();
        final boolean mode = isDigit(charSeq.charAt(index++));
        while (index < length) {
            if (isDigit(charSeq.charAt(index++)) != mode)
            {
            	break;
            }
        }
        return charSeq.subSequence(start, index);
    }

    /**
     * Implements Comparator<CharSequence>.compare
     */
    public int compare(Object obj1, Object obj2) {
    	
    	String firstString = "";
    	String secondString = "";
    	
      	
    	if(DependentAttrUpdate.class.isInstance(obj1) && DependentAttrUpdate.class.isInstance(obj2)){
    		firstString = ((DependentAttrUpdate)obj1).getExecutionOrder();
            secondString = ((DependentAttrUpdate)obj2).getExecutionOrder();
        }
    	
    	if(ApprovalHistory.class.isInstance(obj1) && ApprovalHistory.class.isInstance(obj2)){
    		firstString = Integer.toString(((ApprovalHistory)obj1).getAppSequence());
            secondString = Integer.toString(((ApprovalHistory)obj2).getAppSequence());
        }
    	    	
        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();
 
        int index1 = 0;
        int index2 = 0;
 
        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);
 
            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];
 
            int loc1 = 0;
            int loc2 = 0;
 
            do {
                space1[loc1++] = ch1;
                index1++;
 
                if (index1 < lengthFirstStr) {
                    ch1 = firstString.charAt(index1);
                } else {
                    break;
                }
            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
 
            do {
                space2[loc2++] = ch2;
                index2++;
 
                if (index2 < lengthSecondStr) {
                    ch2 = secondString.charAt(index2);
                } else {
                    break;
                }
            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
 
            String str1 = new String(space1);
            String str2 = new String(space2);
 
            int result;
 
            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
            	if(str1.trim().length()<10 && str2.trim().length()<10){
            		Integer firstNumberToCompare =  Integer.valueOf((Integer
            				.parseInt(str1.trim())));
            		Integer secondNumberToCompare = Integer.valueOf((Integer
            				.parseInt(str2.trim())));
            		result = firstNumberToCompare.compareTo(secondNumberToCompare);
            	}else{
            		BigInteger firstNumberToCompare =  new BigInteger(str1.trim());
            		BigInteger secondNumberToCompare = new BigInteger(str2.trim());
            		result = firstNumberToCompare.compareTo(secondNumberToCompare);
            	}
            } else {
                result = str1.compareToIgnoreCase(str2);
            }
 
            if (result != 0) {
                return result;
            }
        }
        return lengthFirstStr - lengthSecondStr;
    }

}
