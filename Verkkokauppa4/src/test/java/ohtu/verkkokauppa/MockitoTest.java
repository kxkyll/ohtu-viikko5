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
public class MockitoTest {
    
    public MockitoTest() {
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
      verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());        
  }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
