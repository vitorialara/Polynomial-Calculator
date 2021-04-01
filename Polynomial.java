package assignment2;

import java.math.BigInteger;
import java.util.Iterator;

public class Polynomial {
    private SLinkedList<Term> polynomial;

    public int size() {
        return polynomial.size();
    }

    private Polynomial(SLinkedList<Term> p) {
        polynomial = p;
    }

    public Polynomial() {
        polynomial = new SLinkedList<Term>();
    }

    // Returns a deep copy of the object.
    public Polynomial deepClone() {
        return new Polynomial(polynomial.deepClone());
    }

    /*
     * TODO: Add new term to the polynomial. Also ensure the polynomial is in
     * decreasing order of exponent.
     */
    public void addTerm(Term t) {
        boolean added = false;
        if (this.polynomial.size() == 0) {
            this.polynomial.addLast(t);
            added = true;
        }
        else {
            int i = 0;
            for (Term cur : this.polynomial) {
                if (t.getExponent() == cur.getExponent()) {
                    BigInteger newCoefficient = cur.getCoefficient().add(t.getCoefficient());
                    if (newCoefficient.equals("0")){
                        this.polynomial.remove(i);
                        added = true;
                        return;
                    }
                    else {
                        cur.setCoefficient(newCoefficient);
                        added = true;
                        return;
                    }
                }
                else if (t.getExponent() > cur.getExponent()) {
                    this.polynomial.add(i, t);
                    added = true;
                    return;


                }

                i++;
            }
            if (!added) {
                polynomial.addLast(t);
            }
        }




        // Hint: Notice that the function assignment2.SLinkedList.get(index) method is O(n),
        // so if this method were to call the get(index)
        // method n times then the method would be O(n^2).
        // Instead, use a Java enhanced for loop to iterate through
        // the terms of an assignment2.SLinkedList.
        /*
         * for (assignment2.Term currentTerm: polynomial) { // The for loop iterates over each term
         * in the polynomial!! // Example: System.out.println(currentTerm.getExponent())
         * should print the exponents of each term in the polynomial when it is not
         * empty. }
         */
    }

    public Term getTerm(int index) {
        return polynomial.get(index);
    }

    // TODO: Add two polynomial without modifying either
    public static Polynomial add(Polynomial p1, Polynomial p2) { //fix for when they r not the same size

        if (p1.polynomial.size() == 0) {
            return p2;
        }

        if (p2.polynomial.size() == 0) {
            return p1;
        }


        Polynomial addedPolynomial = new Polynomial();



        Iterator<Term> iter1 = p1.polynomial.iterator();
        Term p1Term = iter1.next();


        Iterator<Term> iter2 = p2.polynomial.iterator();
        Term p2Term = iter2.next();

        while (true) {

            if (p1Term.getExponent() > p2Term.getExponent()) {

                addedPolynomial.polynomial.addLast(p1Term);


                if (iter1.hasNext()) {
                    p1Term = iter1.next();

                }
                else {
                    while(iter2.hasNext()) {
                        addedPolynomial.polynomial.addLast(p2Term);
                        p2Term = iter2.next();
                    }
                    addedPolynomial.polynomial.addLast(p2Term);
                    break;
                }
            }


            else if (p2Term.getExponent() > p1Term.getExponent()) {

                addedPolynomial.polynomial.addLast(p2Term);

                if (iter2.hasNext()) {
                    p2Term = iter2.next();
                }
                else {
                    while(iter1.hasNext()) {
                        addedPolynomial.polynomial.addLast(p1Term);
                        p1Term = iter1.next();
                    }
                    addedPolynomial.polynomial.addLast(p1Term);
                    break;
                }
            }



            else {


                if(p1Term.getCoefficient().add(p2Term.getCoefficient()).equals(new BigInteger (Integer.toString(0)))) {

                    if (iter1.hasNext() && iter2.hasNext()) {
                        p1Term = iter1.next();
                        p2Term = iter2.next();
                    }
                    else if(iter1.hasNext()) {
                        p1Term = iter1.next();
                        while(iter1.hasNext()) {
                            addedPolynomial.polynomial.addLast(p1Term);
                            p1Term = iter1.next();
                        }
                        addedPolynomial.polynomial.addLast(p1Term);
                        break;
                    }
                    else if(iter2.hasNext()) {
                        p2Term = iter2.next();
                        while(iter2.hasNext()) {
                            addedPolynomial.polynomial.addLast(p2Term);
                            p2Term = iter2.next();
                        }
                        addedPolynomial.polynomial.addLast(p2Term);
                        break;
                    }
                    else {
                        break;
                    }
                    continue;
                }
                else {
                    Term newTerm = new Term(p1Term.getExponent(), p1Term.getCoefficient().add(p2Term.getCoefficient()));
                    addedPolynomial.polynomial.addLast(newTerm);
                }

                if (iter1.hasNext() && iter2.hasNext()) {
                    p1Term = iter1.next();
                    p2Term = iter2.next();
                }
                else if(iter1.hasNext()) {
                    p1Term = iter1.next();
                    while(iter1.hasNext()) {
                        addedPolynomial.polynomial.addLast(p1Term);
                        p1Term = iter1.next();
                    }
                    addedPolynomial.polynomial.addLast(p1Term);
                    break;
                }
                else if(iter2.hasNext()) {
                    p2Term = iter2.next();
                    while(iter2.hasNext()) {
                        addedPolynomial.polynomial.addLast(p2Term);
                        p2Term = iter2.next();
                    }
                    addedPolynomial.polynomial.addLast(p2Term);
                    break;
                }
                else {
                    break;
                }
            }







        }


        return addedPolynomial;
    }


    // TODO: multiply this polynomial by a given term.
    public void multiplyTerm(Term t) {

        for (Term term : this.polynomial) {


            // change term coefficient
            term.setCoefficient(term.getCoefficient().multiply(t.getCoefficient()));
            //change term exponent
            term.setExponent(term.getExponent()+ t.getExponent());
        }

    }

    // TODO: multiply two polynomials
    public static Polynomial multiply(Polynomial p1, Polynomial p2) { //check time efficiency


        Polynomial p3 = new Polynomial();
        Polynomial p2Clone = p2.deepClone();
        for (Term cur: p1.polynomial) {
            p2Clone.multiplyTerm(cur);
            p3 = add(p3, p2Clone);
            p2Clone = p2.deepClone();
        }



        return p3;




    }

    // TODO: evaluate this polynomial.
    // Hint: The time complexity of eval() must be order O(m),
    // where m is the largest degree of the polynomial. Notice
    // that the function assignment2.SLinkedList.get(index) method is O(m),
    // so if your eval() method were to call the get(index)
    // method m times then your eval method would be O(m^2).
    // Instead, use a Java enhanced for loop to iterate through
    // the terms of an assignment2.SLinkedList.

    public BigInteger eval(BigInteger x) {

        BigInteger value = this.polynomial.get(0).getCoefficient();
        BigInteger[] polyArray = new BigInteger [this.polynomial.get(0).getExponent()+1];
        Iterator<Term> iter1 = this.polynomial.iterator();
        Term pTerm = iter1.next();

        if(iter1.hasNext()) {
            pTerm = iter1.next();
        }

        int i = 0;

        for (Term cur: this.polynomial) {
            if(polyArray.length != 0) {
                polyArray[i] = cur.getCoefficient();
            }
            i++;
            int i2 = 1;
            if((cur.getExponent()- pTerm.getExponent() != 1) && (cur.getExponent() - pTerm.getExponent() !=0)) {
                int difference = cur.getExponent() - pTerm.getExponent();
                while (i2 < difference) {
                    polyArray[i] = new BigInteger(Integer.toString(0));
                    i++;
                    i2++;
                }
            }
            if(iter1.hasNext()) {
                pTerm = iter1.next();
            }
            else {
                if (polyArray.length - 1 != 0) {
                    polyArray[i] = pTerm.getCoefficient();
                    if (pTerm.getCoefficient() != new BigInteger(Integer.toString(0))) {
                        i++;
                        int n = pTerm.getExponent();
                        while(n != 0) {
                            polyArray[i] = new BigInteger(Integer.toString(0));
                            i++;
                            n--;
                        }
                    }
                }
                break;
            }

        }

        i = 0;
        for (BigInteger cur2 : polyArray) {
            if(i == 0) {
                i++;
                continue;
            }
            value = value.multiply(x).add(cur2);

        }



        return value;
    }

    // Checks if this polynomial is a clone of the input polynomial
    public boolean isDeepClone(Polynomial p) {
        if (p == null || polynomial == null || p.polynomial == null || this.size() != p.size())
            return false;

        int index = 0;
        for (Term term0 : polynomial) {
            Term term1 = p.getTerm(index);

            // making sure that p is a deep clone of this
            if (term0.getExponent() != term1.getExponent()
                    || term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
                return false;

            index++;
        }
        return true;
    }

    // This method blindly adds a term to the end of LinkedList polynomial.
    // Avoid using this method in your implementation as it is only used for
    // testing.
    public void addTermLast(Term t) {
        polynomial.addLast(t);
    }

    @Override
    public String toString() {
        if (polynomial.size() == 0)
            return "0";
        return polynomial.toString();
    }
}

