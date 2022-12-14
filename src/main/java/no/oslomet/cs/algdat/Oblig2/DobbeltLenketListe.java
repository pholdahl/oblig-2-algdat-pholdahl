package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");               // godtar ikke a = null; a = {} er greit.
        if (a.length > 0) {                                                     // så lenge a.length er større enn 0
            int i = 0;                                                          // initieres i
            for (; i < a.length && a[i] == null; i++);                          // teller opp i fra 0 til første verdi som ikke er null
            if (i < a.length) {                                                 // så lenge i er mindre enn lengden til tabellen
                Node<T> p = hode = new Node<>(a[i], null, null);    // setter første verdi som ikke er null til hode som første node
                antall++;                                                       // øker antall
                i++;                                                            // øker i, da første verdi som ikke er null er funnet
                for (; i < a.length; i++) {                                     // for løkke på resten av a
                    if (a[i] != null) {                                         // så lenge a[i] ikke er en null verdi
                        p.neste = new Node<>(a[i]);                             // lager en node på p sin neste
                        p.neste.forrige = p;                                    // setter peker til neste p til forrige
                        p = p.neste;                                            // gjør p lik p sin neste
                        antall++;                                               // øker antall
                    }
                }
                hale = p;                                                       // p er siste node, gjør hale lik p
                hale.forrige = p.forrige;                                       // gjør hale sin forrige lik p sin forrige
                hale.neste = null;                                              // gjør hale sin neste lik null
                endringer++;                                                    // å legge inn et array = 1 endring
            }
        }
    }

    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verider!");    // godtar ikke null-verdier
        if(antall == 0){                                                            // hvis antallet er 0, så er det ingen verdier i listen
            hode = hale = new Node<>(verdi, null, null);                // da blir hade lik hode lik den aller første noden
            antall++;                                                               // øker antall
            endringer++;                                                            // øker endringer
            return true;                                                            // returnerer true
        }
        hale.neste = new Node<>(verdi);                                             // hvis antallet er større enn 0 lager vi neste node etter hale
        hale.neste.forrige = hale;                                                  // setter forrige-pekeren til siste node til noden hale peker på
        hale = hale.neste;                                                          // setter hale til å peke på siste node
        antall++;                                                                   // øker antall
        endringer++;                                                                // øker endringer
        return true;

    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");    // godtar ikke null-verdier
        indeksKontroll(indeks, true);                                        // se Liste interface, true = antall er lovlig;
        if(antall == 0 && indeks == 0) {                                            // hvis antall er 0 og indeks er 0
            hode = hale = new Node<>(verdi, null, null);                // da blir hode lik hale lik den aller første noden
            antall++;                                                               // øker antall
            endringer++;                                                            // øker endringer
        } else if (antall > 0 && indeks == 0) {                                     // hvis antall er større enn 0 og indeks er 0, da skal verdi inn foran hode og bli nytt hode
            Node<T> p = hode;                                                       // lagrer hode i en p variabel
            hode = new Node<> (verdi, null, p);                               // gjør hode lik verdi som peker til forrige som er null, og neste som er p
            p.forrige = hode;                                                       // gjør slik at p sin forrige peker på hode
            antall++;                                                               // øker antall
            endringer++;                                                            // øker endringer
        } else if (indeks == antall) {                                              // hvis indeks er lik antall skal verdien inn bakerst
            hale.neste = new Node<>(verdi);                                         // hvis antallet er større enn 0 lager vi neste node etter hale
            hale.neste.forrige = hale;                                              // setter forrige-pekeren til siste node til noden hale peker på
            hale = hale.neste;                                                      // setter hale til å peke på siste node
            antall++;                                                               // øker antall
            endringer++;                                                            // øker endringer
        } else {                                                                    // ellers befinner indeks seg et sted midt inne i lista
            Node<T> p = hode;                                                       // justerer lista fra hode
            for(int i = 1; i < indeks; i++) {                                       // for løkke løper gjennom fra hodet
                p = p.neste;                                                        // p er lik p sin neste node opp til indeks - 1, da er p.neste hvor verdi skal inn
            }
            Node<T> q = p.neste;                                                    // lagrer noden p.neste i q
            p.neste = new Node<>(verdi);                                            // lager en node på p sin neste, fordi det er indeks som er input
            p.neste.forrige = p;                                                    // setter peker den nye noden til p sin forrige
            p = p.neste;                                                            // gjør p lik p sin neste
            p.neste = q;                                                            // setter p sin neste lik q
            q.forrige = p;                                                          // setter q sin forrige lik p
            antall++;                                                               // øker antall
            endringer++;                                                            // øker endringer
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();      // bruker stringBuilder
        s.append("[");                              // starter med  [
        if(antall == 0) {                           // så lenge antallet er 0
            s.append("]");                          // slenger vi bare på en til ]
            return s.toString();                    // returnerer
        }
        Node<T> p = hode;                           // hvis antallet er mer enn 0 lager vi en p variabel til å traversere fra hode til halen
        s.append(p.verdi);                          // legger til den første verdien
        for(int i = 1; i < antall; i++) {           // bruker en for-løkke for å traversere og hente ut resten
            p = p.neste;                            // går videre til neste
            s.append(", ").append(p.verdi);         // legger til , og neste verdi
        }
        s.append("]");                              // etter for løkken legges ] til
        return s.toString();                        // returnerer
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder();      // bruker stringBuilder
        s.append("[");                              // starter med  [
        if(antall == 0) {                           // så lenge antallet er 0
            s.append("]");                          // slenger vi bare på en til ]
            return s.toString();                    // returnerer
        }
        Node<T> p = hale;                           // hvis antallet er mer enn 0 lager vi en p variabel til å traversere fra halen til hode
        s.append(p.verdi);                          // legger til verdien i halen
        for(int i = antall-1; i > 0; i--) {         // begynner på antall-1, siden antall er det sammen som length
            p = p.forrige;                          // går videre til den forrige
            s.append(", ").append(p.verdi);         // legger til , og forrige verdi
        }
        s.append("]");                              // etter for løkken legges ] til
        return s.toString();                        // returnerer
    }

    @Override
    public Iterator<T> iterator() {                                                    // klassisk iterator
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {                                          // iterator med indeks input
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {                   // intern klasse dobbeltLenkeListeIterator som implementerer interfacet Iterator
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;                                                               // p starter på den første i listen
            fjernOK = false;                                                            // blir sann når next() kalles
            iteratorendringer = endringer;                                              // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            if (indeks == 0) {                                                          // hvis indeks = 0
                denne = hode;                                                           // er denne lik hode
            }
            else {                                                                      // ellers så...
                Node<T> p = hode;                                                       // lager en p variabel for å traversere lenketLista
                for(int i = 1; i < indeks; i++) {                                       // for løkke løper gjennom fra hodet
                    p = p.neste;                                                        // p er lik p sin neste node opp til indeks - 1, da skal denne være lik p.neste
                }
                denne = p.neste;                                                        // gjør denne om til p.neste, som er indeks
                fjernOK = false;                                                        // blir sann når next() kalles
                iteratorendringer = endringer;                                          // teller endringer
            }
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if(iteratorendringer != endringer) {                                        // hvis iteratorendringer er ulik endringer
                throw new ConcurrentModificationException("Listen er endret!");         // kaster unntak
            }
            if(!hasNext()) {                                                            // hvis hasNext er false
                throw new NoSuchElementException("Ingen verdier!");                     // kaster unntak
            }
            fjernOK = true;                                                             // fjernOK endres til true
            T p = denne.verdi;                                                          // lagrer denne sin verdi
            denne = denne.neste;                                                        // går videre til denne sin neste

            return p;                                                                   // returnerer denne sin verdi
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
//        Liste<String> liste = new DobbeltLenketListe<>();
//        System.out.println(liste.antall() +  " " + liste.tom());

        Integer[] a = {1,2,3,4,5,6,7};

        // 1,2,3,4,5,6,7

//        List<String> list = new LinkedList<>();

        Liste<Integer> tallListe = new DobbeltLenketListe<>(a);

        System.out.println("Antall i lista: " + tallListe.antall());

//        tallListe.antall();

    }

} // class DobbeltLenketListe


