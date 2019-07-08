package middleware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import storage.Bean;
import storage.Campetto;
import storage.Gioca;
import storage.Partita;
import storage.StorageFacade;

/**
 * Servlet che serve per spedire un invito ad un giocatore
 */
@WebServlet("/CheckInvitiServlet")
public class CheckInvitiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StorageFacade storage;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckInvitiServlet() {
        super();
		storage = StorageFacade.getInstance();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        InputStreamReader isr = new InputStreamReader(request.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String Myemail = br.readLine();
        PrintWriter out = response.getWriter();
	    

		Collection<Bean> partecipazioni = storage.getLista("storage.Gioca");
		for(Bean g: partecipazioni) {
	         
			JsonObject obj = new JsonObject();

			Gioca gioc = (Gioca) g;
			if(gioc.getAtleta_Email().equals(Myemail)) {
				if(gioc.getStatoInvito().equals("riservato")) {
					 Partita p = (Partita) storage.getOggetto("storage.Partita", gioc.getPartita_ID());
			         obj.addProperty("Ora", p.getOra());
			         obj.addProperty("Data", p.getData());
			         obj.addProperty("CodicePartita", p.getID());
					 
					//info Campetto relativo a quella partita
					 Campetto campetto = (Campetto) storage.getOggetto("storage.Campetto", p.getCampetto_ID());
				     obj.addProperty("NomeCampetto",campetto.getNome());
				     obj.addProperty("Citta", campetto.getCitta());
				     obj.addProperty("Tariffa", campetto.getTariffa());
				     
					//al post execute nel client, si salver� questa stringa json e si accender� la campanella
					//Quando si clicca la campanella, si apre una dialog box con le info e con tasto accetta o rifiuta
					//Con accetta si richiamer� la servlet unisciti
					//Con rifiuta si dovr� creare un' altra servlet rifiuta, (che canceller� il record gioca relativo)
					
					    String InvitoItemDaInviare = obj.toString(); //Formatta l'insieme di info in un unica stringa
					    if(p.getStatoPartita().equals("InSospeso"))out.println(InvitoItemDaInviare);
				}
			}
			
		}
		
		
		

	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
