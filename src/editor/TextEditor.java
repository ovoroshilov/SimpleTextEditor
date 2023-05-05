package editor;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    String pathToFile;
    JTextField searchField = new JTextField();
    JTextArea textArea = new JTextArea();
    JFileChooser fileChooser = new JFileChooser();
    WorkWithFile workWithFile = new WorkWithFile();
    JCheckBox useRegExCheckbox;
    boolean isChecked = false;
    List<Integer> searchedWordsIndexes = new ArrayList<>();
    List<Integer> wordsLength = new ArrayList<>();
    Integer index;
    Integer nextCounter = 0;
    int counter = 0;

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setTitle("The first  stage");

        textArea.setName("TextArea");
        fileChooser.setName("FileChooser");
        add(fileChooser);
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
        JScrollPane scrollPane = new JScrollPane(textArea, v, h);
        scrollPane.setName("ScrollPane");
        scrollPane.setBounds(10, 60, 590, 320);
        add(scrollPane);

        searchField.setBounds(95, 20, 250, 35);
        searchField.setName("SearchField");
        add(searchField);

        setJMenuBar(createMenu());

        JButton saveButton = createSaveButton();
        saveButton.setName("SaveButton");
        add(saveButton);

        JButton openButton = createOpenButton();
        openButton.setName("OpenButton");
        add(openButton);

        JButton searchButton = createSearchButton();
        searchButton.setName("StartSearchButton");
        add(searchButton);

        JButton previousMatchButton = createPreviousButton();
        previousMatchButton.setName("PreviousMatchButton");
        add(previousMatchButton);

        JButton nextMatchButton = createNextButton();
        nextMatchButton.setName("NextMatchButton");
        add(nextMatchButton);

        useRegExCheckbox = createCheckBox();
        useRegExCheckbox.setName("UseRegExCheckbox");
        add(useRegExCheckbox);

    }

    private JButton createSaveButton() {
        JButton saveButton = new JButton(new ImageIcon("D:\\35x35saveButton.png"));
        saveButton.setBounds(55, 20, 35, 35);
        saveButton.setVisible(true);
        saveButton.addActionListener(actionEvent -> workWithFile.writeFile(pathToFile, textArea, fileChooser));

        return saveButton;
    }

    private JButton createOpenButton() {
        JButton openButton = new JButton(new ImageIcon("D:\\35x35loadButton.png"));
        openButton.setBounds(10, 20, 35, 35);
        openButton.setVisible(true);
        openButton.addActionListener(actionEvent -> workWithFile.readFile(fileChooser, pathToFile, textArea));
        return openButton;
    }


    private JButton createSearchButton() {
        JButton searchButton = new JButton(new ImageIcon("D:\\35x35searchButton2.png"));
        searchButton.setBounds(350, 20, 35, 35);
        searchButton.addActionListener(actionEvent -> {
            search(textArea, searchField);
        });

        return searchButton;
    }

    private JCheckBox createCheckBox() {
        JCheckBox checkBox = new JCheckBox("Use Regex");
        checkBox.setBounds(475, 20, 90, 35);
        checkBox.addActionListener(actionEvent -> isChecked = !isChecked);
        return checkBox;
    }

    private void search(JTextArea area, JTextField field) {
        String text = area.getText();
        String searchText = field.getText().trim();
        Pattern pattern = Pattern.compile(searchText);
        Matcher matcher = pattern.matcher(text);
        int index = -1;
        int wordLength;
        if (!text.equals(searchField.getText())) {
            searchedWordsIndexes.clear();
            wordsLength.clear();
        }
        if (isChecked) {
            while (matcher.find()) {
                index = matcher.start();
                wordLength = matcher.end() - index;
                searchedWordsIndexes.add(index);
                wordsLength.add(wordLength);
            }
        } else {
            while (true) {
                index = text.indexOf(searchText, index + 1);
                if (index == -1) {
                    break;
                }
                searchedWordsIndexes.add(index);
                wordsLength.add(searchText.length());
            }
        }
        counter = searchedWordsIndexes.size();
        nextCounter = 0;
        if (searchedWordsIndexes.size() > 0) {
            area.setCaretPosition(searchedWordsIndexes.get(0) + wordsLength.get(0));
            area.select(searchedWordsIndexes.get(0), searchedWordsIndexes.get(0) + wordsLength.get(0));
            area.grabFocus();
        }
    }

    private JButton createPreviousButton() {
        JButton previousButton = new JButton(new ImageIcon("D:\\26left.png"));
        previousButton.setBounds(395, 20, 35, 35);
        previousButton.addActionListener(actionEvent -> {
            if (counter > 0) {
                if (nextCounter != 0) {
                    nextCounter--;
                } else {
                    nextCounter = searchedWordsIndexes.size() - 1;
                }
            }
            textArea.setCaretPosition(searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.select(searchedWordsIndexes.get(nextCounter), searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.grabFocus();
        });
        return previousButton;
    }

    private JButton createNextButton() {
        JButton nextButton = new JButton(new ImageIcon("D:\\26x35right.png"));
        nextButton.setBounds(435, 20, 35, 35);
        nextButton.addActionListener(actionEvent -> {
            if (counter > 0) {
                if (counter - 1 > nextCounter) {
                    nextCounter++;
                } else {
                    nextCounter = 0;
                }
            }
            textArea.setCaretPosition(searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.select(searchedWordsIndexes.get(nextCounter), searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.grabFocus();
        });

        return nextButton;
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 550, 30);
        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");
        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        menuFile.setMnemonic(KeyEvent.VK_F);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(actionEvent -> workWithFile.writeFile(pathToFile, textArea, fileChooser));

        JMenuItem openButton = new JMenuItem("Open");
        openButton.setName("MenuOpen");
        openButton.addActionListener(actionEvent -> workWithFile.readFile(fileChooser, pathToFile, textArea));

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(event -> System.exit(0));

        JMenuItem startSearchMenu = new JMenuItem("Start search");
        startSearchMenu.setName("MenuStartSearch");
        startSearchMenu.addActionListener(actionEvent -> {
            search(textArea, searchField);
        });

        JMenuItem previousMenu = new JMenuItem("Previous search");
        previousMenu.setName("MenuPreviousMatch");
        previousMenu.addActionListener(actionEvent -> {
            if (counter > 0) {
                if (nextCounter != 0) {
                    nextCounter--;
                } else {
                    nextCounter = searchedWordsIndexes.size() - 1;
                }
            }
            textArea.setCaretPosition(searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.select(searchedWordsIndexes.get(nextCounter), searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.grabFocus();
        });

        JMenuItem nextMenu = new JMenuItem("Next search");
        nextMenu.setName("MenuNextMatch");
        nextMenu.addActionListener(actionEvent -> {
            if (counter > 0) {
                if (counter - 1 > nextCounter) {
                    nextCounter++;
                } else {
                    nextCounter = 0;
                }
            }
            textArea.setCaretPosition(searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.select(searchedWordsIndexes.get(nextCounter), searchedWordsIndexes.get(nextCounter) + wordsLength.get(nextCounter));
            textArea.grabFocus();
        });

        JMenuItem menuUseRegex = new JMenuItem("Use regular expression");
        menuUseRegex.setName("MenuUseRegExp");
        menuUseRegex.addActionListener(actionEvent -> {
            useRegExCheckbox.doClick();
            search(textArea, searchField);
        });

        menuFile.add(openButton);
        menuFile.add(saveMenuItem);
        menuFile.add(exitMenuItem);

        searchMenu.add(startSearchMenu);
        searchMenu.add(previousMenu);
        searchMenu.add(nextMenu);
        searchMenu.add(menuUseRegex);

        menuBar.add(menuFile);
        menuBar.add(searchMenu);
        return menuBar;
    }
}