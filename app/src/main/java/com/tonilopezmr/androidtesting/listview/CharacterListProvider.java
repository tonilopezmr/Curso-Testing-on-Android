package com.tonilopezmr.androidtesting.listview;

import java.util.LinkedList;

public class CharacterListProvider {
    public static LinkedList<String> characterNames() {
        LinkedList<String> list = new LinkedList();
        list.add("Arya Stark");
        list.add("Bronn");
        list.add("Cersei Lannister");
        list.add("Daenerys Targaryen");
        list.add("Eddard Stark");
        list.add("Hodor");
        list.add("Jaime Lannister");
        list.add("Jon Snow");
        list.add("Jorah Mormont");
        list.add("Khal Drogo");
        list.add("Lord Varys");
        list.add("Margaery Tyrell");
        list.add("Oberyn Martell");
        list.add("Petyr Baelish");
        list.add("Sansa Stark");
        list.add("Stannis Baratheon");
        list.add("Theon Greyjoy");
        list.add("Tyrion Lannister");
        return list;
    }
}
