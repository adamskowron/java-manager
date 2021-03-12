import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonWindowDialog.java
 *
 *
 * Klasa PersonWindowDialog implementuje pomocnicze okna dialogowe
 * umożliwiające utworzenie i wypełnienie danymi nowego obiektu klasy Person
 * oraz modyfikację danych dla istniejącego obiektu klasy Person.
 */
public class PersonWindowDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	
	/*
	 *  Referencja do obiektu, który zawiera dane osoby.
	 */
	private Person person;

	
	// Utworzenie i inicjalizacja komponentów do do budowy
	// okienkowego interfejsu użytkownika
	
	// Font dla etykiet o stałej szerokości znaków
	Font font = new Font("MonoSpaced", Font.BOLD, 12);
	
	// Etykiety wyświetlane na panelu
	JLabel firstNameLabel = new JLabel("      Imię: ");
	JLabel lastNameLabel  = new JLabel("  Nazwisko: ");
	JLabel yearLabel      = new JLabel("   Rok ur.: ");
	JLabel jobLabel       = new JLabel("Stanowisko: ");

	// Pola tekstowe wyświetlane na panelu
	JTextField firstNameField = new JTextField(10);
	JTextField lastNameField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JComboBox<PersonJob> jobBox = new JComboBox<PersonJob>(PersonJob.values());

	// Przyciski wyświetlane na panelu
	JButton OKButton = new JButton("  OK  ");
	JButton CancelButton = new JButton("Anuluj");
	
	
	/*
	 * Konstruktor klasy PersonWindowDialog.
	 *     parent - referencja do okna aplikacji, z którego
     *              zostało wywołane to okno dialogowe.
     *     person - referencja do obiektu reprezentującego osobę,
     *              której dane mają być modyfikowane. 
     *              Jeśli person jest równe null to zostanie utworzony 
     *              nowy obiekt klasy Person
	 */
	private PersonWindowDialog(Window parent, Person person) {
		// Wywołanie konstruktora klasy bazowej JDialog.
		// Ta instrukcja pododuje ustawienie jako rodzica nowego okna dialogowego
		// referencji do tego okna, z którego wywołano to okno dialogowe.
		// Drugi parametr powoduje ustawienie trybu modalności nowego okna diakogowego
		//       - DOCUMENT_MODAL oznacza, że okno rodzica będzie blokowane.
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		// Konfiguracja parametrów tworzonego okna dialogowego
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(220, 200);
		setLocationRelativeTo(parent);
		
		// zapamiętanie referencji do osoby, której dane będą modyfikowane.
		this.person = person;
		
		// Ustawienie tytułu okna oraz wypełnienie zawartości pól tekstowych
		if (person==null){
			setTitle("Nowa osoba");
		} else{
			setTitle(person.toString());
			firstNameField.setText(person.getFirstName());
			lastNameField.setText(person.getLastName());
			yearField.setText(""+person.getBirthYear());
			jobBox.setSelectedItem(person.getJob());
		}
		
		// Dodanie słuchaczy zdarzeń do przycisków.
		// UWAGA: słuchaczem zdarzeń będzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywołana dla
		//        bieżącej instancji okna dialogowego - referencja this
		OKButton.addActionListener( this );
		CancelButton.addActionListener( this );
		
		// Utworzenie głównego panelu okna dialogowego.
		// Domyślnym menedżerem rozdładu dla panelu będzie
		// FlowLayout, który układa wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Zmiana koloru tła głównego panelu okna dialogowego
		panel.setBackground(Color.green);

		// Dodanie i rozmieszczenie na panelu wszystkich komponentów GUI.
		panel.add(firstNameLabel);
		panel.add(firstNameField);
		
		panel.add(lastNameLabel);
		panel.add(lastNameField);
		
		panel.add(yearLabel);
		panel.add(yearField);
		
		panel.add(jobLabel);
		panel.add(jobBox);
		
		panel.add(OKButton);
		panel.add(CancelButton);
		
		// Umieszczenie Panelu w oknie dialogowym.
		setContentPane(panel);
		
		
		// Pokazanie na ekranie okna dialogowego
		// UWAGA: Tą instrukcję należy wykonać jako ostatnią
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponentów GUI.
		// Od tego momentu aplikacja wyświetla nowe okno dialogowe
		// i bokuje główne okno aplikacji, z którego wywołano okno dialogowe
		setVisible(true);
	}
	
	
	/*
	 * Implementacja interfejsu ActionListener.
	 * 
	 * Metoda actionPerformrd bedzie automatycznie wywoływana
	 * do obsługi wszystkich zdarzeń od obiektów, którym jako słuchacza zdarzeń
	 * dołączono obiekt reprezentujący bieżącą instancję okna aplikacji (referencja this) 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, który wygenerował zdarzenie.
		Object source = event.getSource();
		
		if (source == OKButton) {
			try {
				if (person == null) { // Utworzenie nowej osoby
					person = new Person(firstNameField.getText(), lastNameField.getText());
				} else { // Aktualizacji imienia i nazwiska istniejącej osoby
					person.setFirstName(firstNameField.getText());
					person.setLastName(lastNameField.getText());
				}
				// Aktualizacja pozostałych danych osoby
				person.setBirthYear(yearField.getText());
				person.setJob((PersonJob) jobBox.getSelectedItem());
				
				// Zamknięcie okna i zwolnienie wszystkich zasobów.
				dispose();
			} catch (PersonException e) {
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person
				// gdy nie są spełnione ograniczenia nałożone na dopuszczelne wartości 
				// poszczególnych atrybutów.
				// Wyświetlanie modalnego okna dialogowego
				// z komunikatem o błędzie zgłoszonym za pomocą wyjątku PersonException.
				JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (source == CancelButton) {
			// Zamknięcie okna i zwolnienie wszystkich zasobów.
			dispose();
		}
	}
	

	/* 
	 * Metoda tworzy pomocnicze okno dialogowe, które tworzy 
	 * nowy obiekt klasy Person i umożliwia wprowadzenie danych
	 * dla nowo utworzonej osoby.
	 * Jako pierwszy parametr należy przekazać referencję do głównego okna
	 * aplikacji, z którego ta metoda jest wywoływana.
	 * Główne okno aplikacji zostanie zablokowane do momentu zamknięcia
	 * okna dialogowego.
	 * Po zatwierdzeniu zmian przyciskiem OK odbywa się  walidacja poprawności 
	 * danych w konstruktorze i setterach klasy Person. 
	 * Jeśli zostaną wykryte niepoprawne dane to zostanie przechwycony wyjątek 
	 * PersonException i zostanie wyświetlony komunikat o błędzie.
	 * 
	 *  Po poprawnym wypełnieniu danych metoda zamyka okno dialogowe
	 *  i zwraca referencję do nowo utworzonego obiektu klasy Person.
	 */
	public static Person createNewPerson(Window parent) {
		PersonWindowDialog dialog = new PersonWindowDialog(parent, null);
		return dialog.person;
	}

	
	/* 
	 * Metoda tworzy pomocnicze okno dialogowe, które umożliwia 
	 * modyfikację danych osoby reprezentowanej przez obiekt klasy Person,
	 * który został przekazany jako drugi parametr.
	 * Jako pierwszy parametr należy przekazać referencję do głównego okna
	 * aplikacji, z którego ta metoda jest wywoływana.
	 * Główne okno aplikacji zostanie zablokowane do momentu zamknięcia
	 * okna dialogowego.
	 * Po zatwierdzeniu zmian przyciskiem OK odbywa się  walidacja poprawności 
	 * danych w konstruktorze i setterach klasy Person. 
	 * Jeśli zostaną wykryte niepoprawne dane to zostanie przechwycony wyjątek 
	 * PersonException i zostanie wyświetlony komunikat o błędzie.
	 * 
	 *  Po poprawnym wypełnieniu danych metoda aktualizuje dane w obiekcie person
	 *  i zamyka okno dialogowe
	 */
	public static void changePersonData(Window parent, Person person) {
		new PersonWindowDialog(parent, person);
	}

}

