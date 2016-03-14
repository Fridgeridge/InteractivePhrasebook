package org.grammaticalframework.ui.gwt.client;

import java.util.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;


public class FridgeApp implements EntryPoint {

	protected static final String pgfBaseURL = "/grammars";

	protected PGFWrapper pgf;

	protected JSONRequest completeRequest = null;
	protected JSONRequest translateRequest = null;

	private FridgeBagPanel bagPanel;
	private FridgeTextPanel textPanel;
	protected VerticalPanel outputPanel;
	protected StatusPopup statusPopup;

	private FlowPanel prefixPanel;
	private LinkedHashSet<String> prefixes = new LinkedHashSet<String>();

	private int maxMagnets = 100;

	private MagnetFactory magnetFactory;

	//
	// Text
	//

	protected void update () {
		updateBag(getText());
		translate();
	}

	public void updateBag (String text) {
		updateBag(text, "");
	}

	public void updateBag (final String text, String prefix) {
		if (completeRequest != null) {
			completeRequest.cancel();
		}
		final boolean updatePrefixes = prefix.equals("");
		bagPanel.clear();
		bagPanel.addStyleDependentName("empty");
		if (updatePrefixes) { clearPrefixes(); }
		int limit = updatePrefixes ? 0 : maxMagnets; 
		completeRequest = pgf.complete(text + " " + prefix, 
				limit, new PGF.CompleteCallback() {
			public void onResult(IterableJsArray<PGF.Completion> completions) {
				List<Magnet> magnets = new ArrayList<Magnet>();
				for (PGF.Completion completion : completions.iterable()) {
					for (String word : completion.getCompletions()) {
						if (updatePrefixes) {
							addPrefix(text, word.substring(0,1));
						}
						if (magnets.size() < maxMagnets) {
							Magnet magnet = magnetFactory.createMagnet(word, completion.getFrom());
							magnets.add(magnet);
						} else {
							prefixPanel.setVisible(true);
						}
					}
				}
				bagPanel.fill(magnets);
			}
			public void onError(Throwable e) {
				showError("Translation failed", e);
			}
		});
	}

	protected void clearPrefixes () {
		prefixes.clear();
		prefixPanel.clear();
		prefixPanel.setVisible(false);
	}

	protected void addPrefix(final String text, final String prefix) {
		if (prefixes.add(prefix)) {
			Button prefixButton = new Button(prefix, new ClickListener() {		
				public void onClick(Widget sender) {
					updateBag(text, prefix);
				}
			});
			prefixButton.setTitle("Show only magnets stating with '" + prefix + "'");
			prefixPanel.add(prefixButton);
		}
	}

	//
	// Translation
	//

	protected void translate() {
		outputPanel.clear();
		outputPanel.addStyleDependentName("working");
		if (translateRequest != null) {
			translateRequest.cancel();
		}
		translateRequest = pgf.translate(getText(), 
				new PGF.TranslateCallback() {
			public void onResult (IterableJsArray<PGF.TranslationResult> translations) {
				outputPanel.removeStyleDependentName("working");
				for (PGF.TranslationResult tr : translations.iterable()) {
					if (tr.getTranslations() != null)
						for (PGF.Linearizations t : tr.getTranslations().iterable()) {
							for (PGF.Linearization l : t.getLinearizations().iterable()) {
								outputPanel.add(createTranslation(l.getTo(), l.getText()));
							}
						}

					if (tr.getTypeErrors() != null)
						for (PGF.TcError error : tr.getTypeErrors()) {
							SimplePanel panel = new SimplePanel();
							panel.addStyleName("my-typeError");
							panel.add(new HTML("<pre>"+error.getMsg()+"</pre>"));
							outputPanel.add(panel);
						}
				}
			}
			public void onError (Throwable e) {
				showError("Translation failed", e);
			}
		});
	}

	protected ClickListener translationClickListener = new ClickListener () {
		public void onClick(Widget widget) {
			Magnet magnet = (Magnet)widget;
			setInputLanguage(magnet.getLanguage()); // FIXME: this causes an unnecessary update()
			setText(magnet.getText(), magnet.getLanguage());
		}
	};

	protected Widget createTranslation(String language, String text) {
		Magnet magnet = magnetFactory.createUsedMagnet(text, language);
		magnet.addClickListener(translationClickListener);
		String lang = pgf.getLanguageCode(language);
		if (lang != null) {
			magnet.getElement().setLang(lang);
		}
		return magnet;
	}

	//
	// Current text
	//

	public String getText () {
		return textPanel.getText();
	}

	public void setText(String text, String language) {
		textPanel.setText(text, language);
	}

	private void clear() {
		textPanel.clear();
	}


	//
	// Status stuff
	//

	protected void setStatus(String msg) {
		statusPopup.setStatus(msg);
	}

	protected void showError(String msg, Throwable e) {
		statusPopup.showError(msg, e);
	}

	protected void clearStatus() {
		statusPopup.clearStatus();
	}

	// GUI

	protected Widget createUI() {
		ClickListener magnetClickListener = new ClickListener () {
			public void onClick(Widget widget) {
				Magnet magnet = (Magnet)widget;
				textPanel.addMagnet(magnet);
			}
		};
		magnetFactory = new MagnetFactory(magnetClickListener);

		textPanel = new FridgeTextPanel(magnetFactory);
		textPanel.addChangeListener(new ChangeListener() {
			public void onChange(Widget widget) {
				update();
			}
		});
		prefixPanel = new FlowPanel();
		prefixPanel.setStylePrimaryName("my-PrefixPanel");
		bagPanel = new FridgeBagPanel();
		outputPanel = new TranslationsPanel();
		SettingsPanel settingsPanel = new SettingsPanel(pgf, null, statusPopup);
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.setWidth("100%");
		vPanel.add(prefixPanel);
		vPanel.add(bagPanel);

		final DockPanel mainPanel = new DockPanel();
		mainPanel.setStyleName("my-FridgeApp");
		mainPanel.add(textPanel, DockPanel.NORTH);
		mainPanel.add(settingsPanel, DockPanel.SOUTH);
		mainPanel.add(vPanel, DockPanel.CENTER);
		mainPanel.add(outputPanel, DockPanel.EAST);
		
		mainPanel.setCellHeight(vPanel, "100%");
		mainPanel.setCellWidth(vPanel, "80%");
		mainPanel.setCellHeight(outputPanel, "100%");
		mainPanel.setCellWidth(outputPanel, "20%");
		mainPanel.setCellVerticalAlignment(vPanel, HasVerticalAlignment.ALIGN_TOP);
		mainPanel.setCellHorizontalAlignment(outputPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		mainPanel.setCellWidth(settingsPanel, "100%");
		
		Window.addWindowResizeListener(new WindowResizeListener() {
			public void onWindowResized(int w, int h) {
				mainPanel.setPixelSize(w, h);
			}
		});
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		mainPanel.setPixelSize(w, h);
		
		return mainPanel;
	}
	
	private static class TranslationsPanel extends VerticalPanel {
		public TranslationsPanel () {
			setStylePrimaryName("my-TranslationsPanel");
			addStyleDependentName("empty");
		}
		
		public void clear () {
			super.clear();
			addStyleDependentName("empty");
		}

		public void add(Widget w) {
			removeStyleDependentName("empty");
			super.add(w);
		}
		
	}


	//
	// History stuff
	//

	protected class MyHistoryListener implements HistoryListener {
		public void onHistoryChanged(String historyToken) {
			updateSettingsFromHistoryToken();
		}
	};

	protected void updateSettingsFromHistoryToken() {
		updateSettingsFromHistoryToken(History.getToken().split("/"));
	}

	protected void updateSettingsFromHistoryToken(String[] tokenParts) {
		if (tokenParts.length >= 1 && tokenParts[0].length() > 0) {
			setGrammarURL(tokenParts[0]);
		}
		if (tokenParts.length >= 2 && tokenParts[1].length() > 0) {
			setInputLanguage(tokenParts[1]);
		}
	}

	protected void setGrammarURL(String url) {
		if (url != null && !url.equals(pgf.getGrammarURL())) {
			pgf.setGrammarURL(url);
		}
	}

	protected void setInputLanguage (String inputLanguage) {
		if (inputLanguage != null && !inputLanguage.equals(pgf.getInputLanguage())) {
			pgf.setInputLanguage(inputLanguage);	
		}
	}

	//
	// Initialization
	//

	protected class MySettingsListener implements SettingsListener {
		// Will only happen on load
		public void onAvailableGrammarsChanged() {
			if (pgf.getGrammarURL() == null) {
				List<String> grammars = pgf.getGrammars();
				if (!grammars.isEmpty()) {
					pgf.setGrammarURL(grammars.get(0));
				}
			}
		}
		public void onSelectedGrammarChanged() {
			if (pgf.getInputLanguage() == null) {
				pgf.setInputLanguage(pgf.getUserLanguage());
			}
		}
		public void onInputLanguageChanged() {
			clear();
		}
		public void onOutputLanguageChanged() {
			update();
		}
		public void onStartCategoryChanged() {
			update();
		}
		public void onSettingsError(String msg, Throwable e) {
			showError(msg,e);
		}
	}

	public void onModuleLoad() {
		statusPopup = new StatusPopup();

		pgf = new PGFWrapper();
		RootPanel.get().add(createUI());
		pgf.addSettingsListener(new MySettingsListener());
		History.addHistoryListener(new MyHistoryListener());
		updateSettingsFromHistoryToken();
		pgf.updateAvailableGrammars();
	}

}
