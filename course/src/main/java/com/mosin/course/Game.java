package com.mosin.course;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.mosin.course.GameWord.stringToGameWord;

public class Game implements Serializable {

    private static final long serialVersionUID = 7627655182764925754L;

    boolean isFinished = false;

    private Player currentPlayer;

    private List<Player> players = new ArrayList<>();

    private List<String> associationsDict;

    Difficult difficult;

    enum Difficult implements Serializable {
        E, H
    }

    private void addScores(int value) {
        int newScore = currentPlayer.getScore() + value;
        currentPlayer.setScore(newScore);
    }

    private void nextPlayer() {
        int index = players.indexOf(currentPlayer);
        if (index == players.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        currentPlayer = players.get(index);
    }

    private void signUpPlayers() {
        String name = "";
        System.out.println("Введите имя игрока:");
        while (true) {
            name = new Scanner(System.in).nextLine();
            if (name.toUpperCase().equals(Constants.EXIT_FROM_THE_GAME)) {
                break;
            }
            Player player = new Player(name);
            players.add(player);
            System.out.println("Введите имя еще одного игрока\n[Чтобы прекратить задание игроков, введите "
                    + Constants.EXIT_FROM_THE_GAME + "]");
        }
        currentPlayer = players.get(0);                 // Первый будет ходить первый из списка игроков
        System.out.println("\nВ игре принимают участие игроки: " + Player.printPlayers(players));
    }

    public void setDifficult(Difficult difficult) {
        this.difficult = difficult;
    }

    private void finishTheGame() {
        this.isFinished = true;
    }

    private void printGameResults() {
        System.out.println("\nИгра завершена");
        for (Player player : players) {
            System.out.println(player);
        }
    }

    // Загружаем словарь с диска в переменную
    private List<String> getAssociationsDict() {
        String pathToFile = Constants.PATH_TO_RESOURCES + "dict.txt";
        List<String> dict = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathToFile), StandardCharsets.UTF_8))) {
            String line;
            while (((line = br.readLine()) != null) && (line.length() != 0)) {
                dict.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + pathToFile + " не найден");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + pathToFile);
            e.printStackTrace();
        }

        return dict;
    }

    // Создание новой игры
    public void newGame() {
        // Выбор сложности
        chooseDifficult();
        // Формирование списка игроков
        signUpPlayers();
        // Загрузка словаря
        associationsDict = getAssociationsDict();
    }

    private void chooseDifficult() {
        String difficult = "";
        Scanner input = new Scanner(System.in);
        while (!difficult.toLowerCase().equals("e") &&
                !difficult.toLowerCase().equals("easy") &&
                !difficult.toLowerCase().equals("h") &&
                !difficult.toLowerCase().equals("hard")) {
            System.out.println("Выберите сложность\n[Легкая - введите E (Easy), тяжелая - H (Hard)]");
            difficult = input.nextLine();
        }

        if (difficult.toLowerCase().equals("e") || difficult.toLowerCase().equals("easy")) {
            System.out.println("Выбрана легкая сложность (E - Easy)");
            setDifficult(Difficult.E);
        } else if (difficult.toLowerCase().equals("h") || difficult.toLowerCase().equals("hard")) {
            System.out.println("Выбрана тяжелая сложность (H - Hard)");
            setDifficult(Difficult.H);
        } else {
            System.out.println("Выбрана некорректная сложность");
        }
    }

    public Game loadGame(String saveFileName) {
        Game loadGame = new Game();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFileName))) {
            loadGame = (Game) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return loadGame;
    }

    public void gameProcessWithLoad() {
        System.out.format("[Чтобы начать новую игру, введите %s]", Constants.NEW_GAME);
        System.out.format("[Чтобы загрузить, введите %s]\n", Constants.LOAD_GAME);
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();

        Game game = new Game();
        switch (command.toUpperCase()) {
            case Constants.NEW_GAME:
                newGame();
                game = this;
                break;
            case Constants.LOAD_GAME:
                System.out.format("[Введите имя файла:]");
                String fileName = input.nextLine();
                game = loadGame(Constants.PATH_TO_RESOURCES + fileName);
                break;
        }
        gameInProgress(game);
    }

    private void gameInProgress(Game game) {
        for (int i = 0; i < game.associationsDict.size(); i++) {
            GameWord gameWord = stringToGameWord(game.associationsDict.get(i));
            System.out.println("\nХодит игрок: " + game.currentPlayer);

            int scores = game.round(gameWord, i);
            game.addScores(scores);
            if (game.isFinished) {
                break;
            }
        }
        game.printGameResults();
    }

    public void gameProcess() {
        // Выбор сложности
        chooseDifficult();
        // Формирование списка игроков
        signUpPlayers();
        // Формирование словаря
        associationsDict = getAssociationsDict();

        for (int i = 0; i < associationsDict.size(); i++) {
//            GameWord gameWord = stringToGameWord(dictLine);
            GameWord gameWord = stringToGameWord(associationsDict.get(i));
            System.out.println("\nХодит игрок: " + currentPlayer);

            int scores = round(gameWord, i);
            addScores(scores);
            if (isFinished) {
                break;
            }
        }
        printGameResults();
    }

    private int round(GameWord gameWord, int positionInDict) {
        System.out.println("Внимание, первая ассоциация: ");
        int award = 3;   // Базовое количество очков
        // вызываем сортировку
        // перенести ее в GameWord
        gameWord.associations = sortTheAssoc(gameWord.associations, this.difficult);
        Collection<String> listAssoc = gameWord.associations.values();
        for (String assoc : listAssoc) {
            System.out.println("*" + assoc + "*");
            System.out.println("Угадали слово? Введите ваш вариант ");
            System.out.print("[Чтобы увидеть доп. ассоциацию, введите N]/");
            System.out.print("[Чтобы перейти к следующему слову, введите W]/");
            System.out.print("[Чтобы сохранить прогресс, введите S]/");
            System.out.println("[Чтобы завершить игру, введите X]");
            Scanner input = new Scanner(System.in);
            String command = input.nextLine();
            if (command.toUpperCase().equals("N")) {
                if (!assoc.equals(listAssoc.toArray()[listAssoc.size() - 1]))
                    System.out.println("Дополнительная ассоциация:");
                award--;
            } else if (command.toUpperCase().equals("W")) {
                System.out.println("Переход к следующему слову");
                award = 0;
                break;
            } else if (command.toUpperCase().equals("X")) {
                System.out.println("Завершение игры");
                award = 0;
                finishTheGame();
                break;
            } else if (command.toUpperCase().equals("S")) {
                System.out.println("Сохранение игры");
                award = 0;
                saveTheGame(gameWord, positionInDict);
                break;
            } else if (command.toLowerCase().equals(gameWord.word.toLowerCase())) {
                System.out.println("Поздравляем. Вы угадали слово!!");
                break;
            } else {
                System.out.println("К сожалению, ответ неверный\nПереход хода");
                award = 0;
                nextPlayer();
                break;
            }
        }
        System.out.println("Вы получаете " + award + " балла(ов)");
        return award;
    }

    private void saveTheGame(GameWord lastWord, int positionInDict) {
        for (int i = 0; i <= positionInDict; i++) {
            associationsDict.remove(0);
        }

        String fileName = Player.printPlayers(players);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.PATH_TO_RESOURCES + fileName + ".dat"))) {
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + Constants.PATH_TO_RESOURCES + fileName + " не найден");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch(Exception ex){
//            System.out.println(ex.getMessage() + " случилась беда при сохранении");
//        }
    }

//    private void loadTheGame(String fileName) {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
//
//            this = (Game) ois.readObject();
//        } catch (Exception ex) {
//
//            System.out.println(ex.getMessage());
//        }
//    }



    // Сортировка мапы с ассоциациями. Для легкой сложности сортировка по убыванию частотности (сначала показываем самое популярное слово),
    // для тяжелой сложность сортировка по возврастанию частостности (сначала показываем самое непопулярное слово)
    private SortedMap<Integer, String> sortTheAssoc(SortedMap<Integer, String> assoc, Difficult difficult) {
        SortedMap<Integer, String> sortedAssoc;
        if (difficult.equals(Difficult.E)) {
            sortedAssoc = new TreeMap<>(Collections.reverseOrder());
        } else {
            sortedAssoc = new TreeMap<>();
        }
        sortedAssoc.putAll(assoc);
        return sortedAssoc;
    }
}
