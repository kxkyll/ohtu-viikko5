/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.verkkokauppa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 *
 * @author kxkyllon
 */
public class KauppaTest {

    public KauppaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ostoksenPaaytyttyapankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
    }

    @Test
    public void ostoksenPaaytyttyapankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("maija", "56789");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 1, "56789", "33333-44455", 5);
    }

    @Test
    public void kahdenEriTuotteenostoksenPaaytyttyapankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 3));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("maija", "56789");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 1, "56789", "33333-44455", 8);
    }

    @Test
    public void kahdenSamanTuotteenOsto() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));


        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("maija", "56789");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 1, "56789", "33333-44455", 10);
    }

    @Test
    public void kahdenEriTuotteenJoistaToistaEiVarastossaOsto() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 3));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("maija", "56789");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 1, "56789", "33333-44455", 5);
    }

    @Test
    public void kaupanAloitaAsiointiNollaaVanhatOstokset() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));


        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.aloitaAsiointi();
        k.tilimaksu("maija", "56789");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 1, "56789", "33333-44455", 0);
    }
    
    @Test
    public void kauppaPyytaaUudenViitenumeronJokaiselleMaksutapahtumalle() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1).thenReturn(2);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 3));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("maija", "56789");
        
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("maija", "56789");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 2, "56789", "33333-44455", 3);
    }
    
    @Test
    public void kauppaosaaPoistaaOstoskoriinLisatynTuotteen() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 3));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.poistaKorista(1);
        k.tilimaksu("maija", "56789");
        
        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        //boolean tilisiirto(String nimi, int viitenumero, String tililta, String tilille, int summa);
        // anyString(), anyInt(), anyString(), anyString(), anyInt()
        verify(pankki).tilisiirto("maija", 1, "56789", "33333-44455", 3);
    }
}
