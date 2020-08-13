package com.github.acceler8tion.multilanguagescripter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

public class Translator {

    private final Map<String, Properties> languages = new HashMap<>();

    private Translator(){

    }

    public static Translator build() {
        return new Translator();
    }

    public Translator register(String lang, InputStream stream) throws IOException {
        Properties prop = new Properties();
        prop.load(stream);
        languages.put(lang, prop);

        return this;
    }

    public Properties load(String lang) throws NoSuchLanguageException{
        if(!languages.containsKey(lang)) throw new NoSuchLanguageException(String.format("Language `%s` do not exist.", lang));
        return languages.get(lang);
    }

    public String translate(String lang, String key) throws NoSuchLanguageException, NoSuchScriptException{
        if(!languages.containsKey(lang)) throw new NoSuchLanguageException(String.format("Language `%s` do not exist.", lang));
        String sc = languages.get(lang).getProperty(key);
        if(sc == null) throw new NoSuchScriptException(String.format("Script `%s do not exist.", key));
        return sc;
    }

    public String[] translate(String lang, String... keys) throws NoSuchLanguageException, NoSuchScriptException{
        if(!languages.containsKey(lang)) throw new NoSuchLanguageException(String.format("Language `%s` do not exist.", lang));
        String[] scripts = new String[keys.length];
        for(int i = 0; i < keys.length; i++){
            String sc = languages.get(lang).getProperty(keys[i]);
            if(sc == null) throw new NoSuchScriptException(String.format("Script `%s do not exist.", keys[i]));
            scripts[i] = sc;
        }
        return scripts;
    }

    public void translate(String lang, String key, Consumer<String> consumer) throws NoSuchLanguageException, NoSuchScriptException{
        if(!languages.containsKey(lang)) throw new NoSuchLanguageException(String.format("Language `%s` do not exist.", lang));
        String sc = languages.get(lang).getProperty(key);
        if(sc == null) throw new NoSuchScriptException(String.format("Script `%s do not exist.", key));
        consumer.accept(sc);
    }

    public void translate(String lang, Consumer<String[]> consumer, String... keys) throws NoSuchLanguageException, NoSuchScriptException{
        if(!languages.containsKey(lang)) throw new NoSuchLanguageException(String.format("Language `%s` do not exist.", lang));
        String[] scripts = new String[keys.length];
        for(int i = 0; i < keys.length; i++){
            String sc = languages.get(lang).getProperty(keys[i]);
            if(sc == null) throw new NoSuchScriptException(String.format("Script `%s do not exist.", keys[i]));
            scripts[i] = sc;
        }
        consumer.accept(scripts);
    }
}
