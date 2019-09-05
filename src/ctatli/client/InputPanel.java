package ctatli.client;

import ctatli.server.Message;
import org.javatuples.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class InputPanel extends JPanel {

    private Client client;

    InputPanel(Client client, JTextArea responseArea){
        this.client = client;
        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);

        JPanel addWordContainer = new JPanel();

        JPanel wordInputContainer = new JPanel();
        JLabel wordInputLabel = new JLabel("Word :");
        JTextField addWord = new JTextField(10);
        wordInputContainer.add(wordInputLabel);
        wordInputContainer.add(addWord);

        JPanel definitionInputContainer = new JPanel();
        JLabel definitionInputLabel = new JLabel("Definition :");
        JTextArea definition = new JTextArea(5,50);
        definitionInputContainer.add(definitionInputLabel);
        definitionInputContainer.add(definition);

        addWordContainer.add(wordInputContainer);
        addWordContainer.add(definitionInputContainer);
        addWordContainer.setLayout(new BoxLayout(addWordContainer, BoxLayout.Y_AXIS));

        JButton addButton = new JButton("Add");
        JPanel addWordInputContainer = new JPanel();
        addWordInputContainer.add(addWordContainer);
        addWordInputContainer.add(addButton);
        addWordInputContainer.setLayout(new BoxLayout(addWordInputContainer, BoxLayout.X_AXIS));


        JPanel deleteWordContainer = new JPanel();

        JPanel deleteInputContainer = new JPanel();
        JLabel deleteInputLabel = new JLabel("Word :");
        JTextField deleteWord = new JTextField(10);
        deleteInputContainer.add(deleteInputLabel);
        deleteInputContainer.add(deleteWord);

        deleteWordContainer.add(deleteInputContainer);

        JButton deleteButton = new JButton("Delete");
        deleteWordContainer.add(deleteButton);
        deleteWordContainer.setLayout(new BoxLayout(deleteWordContainer, BoxLayout.X_AXIS));


        JPanel queryWordContainer = new JPanel();

        JPanel queryInputContainer = new JPanel();
        JLabel queryInputLabel = new JLabel("Word :");
        JTextField queryWord = new JTextField(10);
        queryInputContainer.add(queryInputLabel);
        queryInputContainer.add(queryWord);

        queryWordContainer.add(queryInputContainer);

        JButton queryButton = new JButton("Search");
        queryWordContainer.add(queryButton);
        queryWordContainer.setLayout(new BoxLayout(queryWordContainer, BoxLayout.X_AXIS));


        add(addWordInputContainer, "add");
        add(deleteWordContainer, "delete");
        add(queryWordContainer, "query");


        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input = queryWord.getText();
                if(IsValidInput(input)){
                    Message message = new Message(Message.MessageType.LOOKUP, SanitiseInput(queryWord.getText()));
                    client.SendMessage(message);
                }
                else {
                    responseArea.setText(String.format("%s is not a valid word, your input must only contain letters and hyphens", input));
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input = deleteWord.getText();
                if(IsValidInput(input)){
                    Message message = new Message(Message.MessageType.DELETE, SanitiseInput(input));
                    client.SendMessage(message);
                }
                else {
                    responseArea.setText((String.format("%s is not a valid word, your input must only contain letters and hyphens", input)));
                }

            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String wordInput = addWord.getText();
                String definitionInput = definition.getText();
                if(definitionInput.length() == 0) {
                	responseArea.setText((String.format("Please enter a definition for %s", wordInput)));
                	return;
                }
                
                ArrayList<String> definitions = ParseDefinitionInput(definitionInput);
                

                if(IsValidInput(wordInput)){
                    Message message = new Message(Message.MessageType.ADD, new Pair<>(SanitiseInput(wordInput), definitions));
                    client.SendMessage(message);
                }
                else {
                    responseArea.setText((String.format("%s is not a valid word, your input must only contain letters and hyphens", wordInput)));
                }
            }
        });

    }

    private ArrayList<String> ParseDefinitionInput(String input){
        ArrayList<String> defintions = new ArrayList<>(Arrays.asList(input.split("\\n")));
        return defintions;
    }

    private String SanitiseInput(String input){
        return  input.toLowerCase();
    }

    private boolean IsValidInput(String input){
        return input.matches("[A-Z|a-z|\\-]+");
    }
}
