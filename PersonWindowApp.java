import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * Program: Aplikacja okienkowa z GUI, która umo¿liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonWindowApp.java
 *          
 *   Autor: Pawe³ Rogalinski
 *    Data: pazdziernik 2017 r.
 */
public class CarWindowApp extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja okienkowa\n" + 
	        "Autor: Pawe³ Rogalinski\n" + 
			"Data:  paŸdziernik 2017 r.\n";
	
	
	public static void main(String[] args) {
		// Utworzenie obiektu reprezentuj¹cego g³ówne okno aplikacji.
		// Po utworzeniu obiektu na pulpicie zostanie wyœwietlone
		// g³ówne okno aplikacji.
		new CarWindowApp();
		
		// UWAGA: mo¿na utworzyæ kilka okien aplikacji.
		// Wsystkie okna bêd¹ wyœwietlone w tym samum miejscu na pulpicie.
		// Po uruchomieniu kilku okien nale¿y je rêcznie porozsuwaæ.
		// Ka¿de okno dzia³a niezale¿nie od pozosta³ych.
		// Aplikacja koñczy swoje dzia³anie po zamkniêciu wszystkich utworzonych okien
		// lub po naciœniêciu przycisku "Zakoñcz aplikacjê",
		// 
		// Je¿eli chcesz wypróbowaæ dzia³anie kilku okien odkomentój poni¿sze linie
		// new PersonWindowApp();
		// new PersonWindowApp();
	}

	
	
	/*
	 *  Referencja do obiektu, który zawiera dane aktualnej osoby.
	 */
	private Car currentCar;
	
	
	// Font dla etykiet o sta³ej szerokoœci znaków
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Etykiety wyœwietlane na panelu w g³ównym oknie aplikacji
	JLabel BrandNameLabel = new JLabel("      Marka: ");
	JLabel ModelNameLabel  = new JLabel("  Model: ");
	JLabel yearLabel      = new JLabel("   Rok produkcji: ");
	JLabel powerlaber	= new Jlabel("Moc:")
	JLabel BodyLabel       = new JLabel("Typ nadwozia: ");

	// Pola tekstowe wyœwietlane na panelu w g³ównym oknie aplikacji
	JTextField BrandNameField = new JTextField(10);
	JTextField ModelNameField    = new JTextField(10);
	JTextField PowerField	= new JTextField(10);
	JTextField yearField    = new JTextField(10);
	JTextField BodyField     = new JTextField(10);

	// Przyciski wyœwietlane na panelu w g³ównym oknie aplikacji
	JButton newButton    = new JButton("Nowy samochód");
	JButton editButton   = new JButton("Zmieñ dane");
	JButton saveButton   = new JButton("Zapisz do pliku");
	JButton loadButton   = new JButton("Wczytaj z pliku");
	JButton deleteButton = new JButton("Usuñ samochód");
	JButton infoButton   = new JButton("O programie");
	JButton exitButton   = new JButton("Zakoñcz aplikacjê");

	
	/*
	 * Utworzenie i konfiguracja g³ównego okna apkikacji
	 */
	public PersonWindowApp(){
		// Konfiguracja parametrów g³ównego okna aplikacji
		setTitle("CarWindowApp");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 270);
		setResizable(false);
		setLocationRelativeTo(null);

		// Zmiana domyœlnego fontu dla wszystkich etykiet
		// U¿yto fontu o sta³ej szerokoœci znaków, by wyrównaæ
		// szerokoœæ wszystkich etykiet.
		lastNameLabel.setFont(font);
		firstNameLabel.setFont(font);
		yearLabel.setFont(font);
		jobLabel.setFont(font);

		// Zablokowanie mo¿liwoœci edycji tekstów we wszystkich 
		// polach tekstowych.  (pola nieedytowalne)
		firstNameField.setEditable(false);
		lastNameField.setEditable(false);
		yearField.setEditable(false);
		jobField.setEditable(false);

		
		// Dodanie s³uchaczy zdarzeñ do wszystkich przycisków.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywo³ana dla
		//        bie¿¹cej instancji okna aplikacji - referencja this
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		// Utworzenie g³ównego panelu okna aplikacji.
		// Domyœlnym mened¿erem rozd³adu dla panelu bêdzie
		// FlowLayout, który uk³ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Dodanie i rozmieszczenie na panelu wszystkich
		// komponentów GUI.
		panel.add(BrandNameLabel);
		panel.add(BrandNameField);
		
		panel.add(ModelNameLabel);
		panel.add(ModelNameField);
		
		panel.add(yearLabel);
		panel.add(yearField);
		
		panel.add(powerLabel);
		panel.add(powerField)
		
		panel.add(jobLabel);
		panel.add(jobField);

		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(editButton);
		panel.add(infoButton);
		panel.add(exitButton);
		
		// Umieszczenie Panelu w g³ównym oknie aplikacji.
		setContentPane(panel);
		
		// Wype³nienie pól tekstowych danymi aktualnej osoby.
		showCurrentCar();
		
		// Pokazanie na ekranie g³ównego okna aplikacji
		// UWAGA: T¹ instrukcjê nale¿y wykonaæ jako ostatni¹
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponentów GUI.
		// Od tego momentu aplikacja uruchamia g³ówn¹ pêtlê zdarzeñ
		// która dzia³a w nowym w¹tku niezale¿nie od pozosta³ej czêœci programu.
		setVisible(true);
	}

	
	/*
	 * Metoda wype³nia wszystkie pola tekstowe danymi
	 * aktualnej osoby.
	 */
	void showCurrentCar() {
		if (currentcar == null) {
			firstNameField.setText("");
			lastNameField.setText("");
			yearField.setText("");
			powerField.setText("")
			jobField.setText("");
		} else {
			BrandField.setText(currentCar.getBrandName());
			ModelField.setText(currentCar.getModelName());
			yearField.setText("" + currentCar.getYear());
			powerField.setText("" = currnetCar.getHorsePower());
			BodyField.setText("" + currentCar.getBody());
		}
	}

	
	/*
	 * Implementacja interfejsu ActionListener.
	 * 
	 * Metoda actionPerformrd bedzie automatycznie wywo³ywana
	 * do obs³ugi wszystkich zdarzeñ od obiektów, którym jako s³uchacza zdarzeñ
	 * do³¹czono obiekt reprezentuj¹cy bie¿¹c¹ instancjê okna aplikacji (referencja this) 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, który wygenerowa³ zdarzenie.
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) { 
				currentCar = CarWindowDialog.createNewCar(this);
			}
			if (eventSource == deleteButton) {
				currentCar = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
				Car.printToFile(fileName, currentCar);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku. 
				currentCar = Car.readFromFile(fileName);
			}
			if (eventSource == editButton) {
				if (currentCar == null) throw new CarException("¯aden samochód nie zosta³ utworzony."); 
				CarWindowDialog.changeCarData(this, currentCar);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (CarException e) {
			// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
			// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci 
			// poszczególnych atrybutów.
			// Wyœwietlanie modalnego okna dialogowego
			// z komunikatem o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
		// Aktualizacja zawartoœci wszystkich pól tekstowych.
		showCurrentCar();
	}	
	
	
} // koniec klasy PersonWindowApp
