package org.grammaticalframework.ui.gwt.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import com.google.gwt.http.client.*;
import com.google.gwt.xml.client.*;
import com.google.gwt.core.client.*;

public class PGFWrapper {

	private String grammarURL = null;

	private PGF pgf;

	private String inputLanguage = null;

	private String outputLanguage = null;

	private String cat = null;

	// Cached info about the available grammars 

	private List<String> grammars;

	// Cached info about the currently selected grammar
	
	private String userLanguage;
	
	private LinkedHashMap<String,PGF.Language> languages;

	private JsArrayString categories;
	private JsArrayString functions;

	// Event listeners
	
	private List<SettingsListener> listeners = new LinkedList<SettingsListener>();
	

	public PGFWrapper() {
		this.pgf = new PGF();
	}

	public void updateAvailableGrammars() {
		String url = "/grammars.xml";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		try
		{
			builder.sendRequest(null, new RequestCallback() {
				public void onResponseReceived(Request request, Response response)
				{
					if (200 == response.getStatusCode())
					{
						grammars = new ArrayList<String>();
						try
						{
							Document grammarsDoc = XMLParser.parse(response.getText());

							NodeList grammarsList = grammarsDoc.getElementsByTagName("grammar");
							for (int i = 0; i < grammarsList.getLength(); i++)
							{
								Node grammarNode = grammarsList.item(i);
								grammars.add(((Element)grammarNode).getAttribute("name"));
							}
						}
						catch (DOMException e)
						{
							fireSettingsError("Could not parse XML document.", e);
						}
						fireAvailableGrammarsChanged();
					}
					else
					{
						fireSettingsError("Error getting grammar list", null);
					}
				}

				public void onError(Request request, Throwable e)
				{
					fireSettingsError("Error getting grammar list", e);
				}
			});
		}
		catch (RequestException e)
		{
			fireSettingsError("Couldn't connect to server", e);
		}
	}
	
	protected void updateSelectedGrammar () {
		if (grammarURL == null)
			return;

		clearCachedInfo();
		pgf.grammar(grammarURL, new PGF.GrammarCallback() {
			public void onResult(PGF.Grammar grammar) {
				userLanguage = grammar.getUserLanguage();
				languages = new LinkedHashMap<String,PGF.Language>();
				for (PGF.Language l : grammar.getLanguages().iterable()) {
					String name = l.getName();
					languages.put(name, l);
				}
				
				categories = grammar.getCategories();
				functions = grammar.getFunctions();

				fireSelectedGrammarChanged();
			}

			public void onError (Throwable e) {
				fireSettingsError("Error getting language information", e);
			}
		});
	}

	//
	// PGF functionality
	//
	
	public JSONRequest translate (String input, final PGF.TranslateCallback callback) {
		return pgf.translate(grammarURL, input, inputLanguage, cat, outputLanguage, callback);
	}

	public JSONRequest complete (String input, int limit, final PGF.CompleteCallback callback) {
		return pgf.complete(grammarURL, input, inputLanguage, cat, limit, callback);
	}

	public JSONRequest parse (String input, final PGF.ParseCallback callback) {
		return pgf.parse(grammarURL, input, inputLanguage, cat, callback);
	}

	public JSONRequest linearize (String tree, final PGF.LinearizeCallback callback) {
		return pgf.linearize(grammarURL, tree, outputLanguage, callback);
	}

	public String graphvizAbstractTree(String abstractTree) {
		return pgf.graphvizAbstractTree(grammarURL,abstractTree);
	}

	public String graphvizParseTree(String abstractTree, String lang) {
		return pgf.graphvizParseTree(grammarURL,abstractTree,lang);
	}

	public String graphvizAlignment(String abstractTree) {
		return pgf.graphvizAlignment(grammarURL,abstractTree);
	}

	public Request browse(String id, String href, String cssClass, RequestCallback callback) {
		return pgf.browse(grammarURL, id, href, cssClass, callback);
	}

	public JSONRequest generateAll(String cat, int depth, int limit, PGF.GenerationCallback callback) {
		return pgf.generateAll(grammarURL, cat, depth, limit, outputLanguage, callback);
	}

	public JSONRequest generateRandom(String cat, int depth, int limit, PGF.GenerationCallback callback) {
		return pgf.generateRandom(grammarURL, cat, depth, limit, outputLanguage, callback);
	}

	//
	// Settings
	//

	public String getGrammarURL() {
		return grammarURL;
	}

	public void setGrammarURL(String grammarURL) {
		this.grammarURL = grammarURL;
		this.inputLanguage = null;
		this.outputLanguage = null;
		this.cat = null;
		updateSelectedGrammar();
	}

	public String getInputLanguage() {
		return inputLanguage;
	}

	public void setInputLanguage(String inputLanguage) {
		this.inputLanguage = inputLanguage;
		fireInputLanguageChanged();
	}

	public String getOutputLanguage() {
		return outputLanguage;
	}

	public void setOutputLanguage(String outputLanguage) {
		this.outputLanguage = outputLanguage;
		fireOutputLanguageChanged();
	}

	public String getStartCategory() {
		return cat;
	}

	public void setStartCategory(String cat) {
		this.cat = cat;
		fireStartCategoryChanged();
	}

	public JsArrayString getCategories() {
		return categories;
	}

	public JsArrayString getFunctions() {
		return functions;
	}
	
	
	//
	// Information about the available grammars
	//
	public List<String> getGrammars() {
		return grammars;
	}

	//
	// Information about the selected grammar
	//
	private void clearCachedInfo () {
		languages = null;
	}

	public String getUserLanguage () {
		return userLanguage;
	}

	public String getLanguageCode (String language) {
		PGF.Language l = languages.get(language);
		return l == null ? null : l.getLanguageCode();
	}

	public Collection<String> getAllLanguages() {
		return languages.keySet();
	}
	
	//
	// Listeners
	//
	
	public static class SettingsAdapter implements SettingsListener {
		public void onAvailableGrammarsChanged() {}
		public void onSelectedGrammarChanged() {}
		public void onInputLanguageChanged() {}
		public void onOutputLanguageChanged() {}
		public void onStartCategoryChanged() {}
		public void onSettingsError(String msg, Throwable e) {}
	}

	public void addSettingsListener(SettingsListener listener) {
		listeners.add(listener);
	}

	protected void fireAvailableGrammarsChanged() {
		for (SettingsListener listener : listeners) {
			listener.onAvailableGrammarsChanged();
		}
	}

	protected void fireSelectedGrammarChanged() {
		for (SettingsListener listener : listeners) {
			listener.onSelectedGrammarChanged();
		}
	}

	protected void fireInputLanguageChanged() {
		for (SettingsListener listener : listeners) {
			listener.onInputLanguageChanged();
		}
	}

	protected void fireOutputLanguageChanged() {
		for (SettingsListener listener : listeners) {
			listener.onOutputLanguageChanged();
		}
	}
	
	protected void fireStartCategoryChanged() {
		for (SettingsListener listener : listeners) {
			listener.onStartCategoryChanged();
		}
	}
	
	protected void fireSettingsError(String msg, Throwable e) {
		for (SettingsListener listener : listeners) {
			listener.onSettingsError(msg, e);
		}
	}
	
}
