package com.mosin.course;

import java.io.BufferedReader;
import java.io.File;
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

    boolean isFinished = false;                         // признак завершения игры

    private Player currentPlayer;                       // игрок, который выполняет ход

    private List<Player> players = new ArrayList<>();   // список всех игроков

    private List<String> associationsDict;

    Difficult difficult;

    enum Difficult implements Serializable {
        E, H
    }

    public void setDifficult(Difficult difficult) {
        this.difficult = difficult;
    }

    private void addScores(int value) {
        int newScore = currentPlayer.getScore() + value;
        currentPlayer.setScore(newScore);
    }

    // Переход хода к следующему игроку
    private void nextPlayer() {
        int index = players.indexOf(currentPlayer);
        if (index == players.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        currentPlayer = players.get(index);
    }

    // Регистрация игроков
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

    private void finishTheGame() {
        this.isFinished = true;
    }

    private void printGameResults() {
        System.out.println("\nИгра завершена");
        for (Player player : players) {
            System.out.println(player);
        }
    }

    // Инициализируем словарь, загружая его с диска в переменную
    private void initAssociationsDict() {
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
            System.out.println("Ошибка при чтении файла " + pathToFile);
            e.printStackTrace();
        }

        this.associationsDict = dict;
    }

    // Создание новой игры
    private void newGame() {
        // Выбор сложности
        chooseDifficult();
        // Формирование списка игроков
        signUpPlayers();
        // Загрузка словаря
        initAssociationsDict();
    }

    // Загрузка прогресса игры из файла
    private Game loadGame(String saveFileName) {
        Game loadGame = new Game();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFileName))) {
            loadGame = (Game) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return loadGame;
    }

    private void chooseDifficult() {
        String difficult = "";
        Scanner input = new Scanner(System.in);
        while (!difficult.toLowerCase().equals("e") &&
                !difficult.toLowerCase().equals("h")) {
            System.out.println("Выберите сложность\n[Легкая - введите E (Easy), тяжелая - H (Hard)]");
            difficult = input.nextLine();
        }

        if (difficult.toLowerCase().equals("e")) {
            System.out.println("Выбрана легкая сложность (E - Easy)");
            setDifficult(Difficult.E);
        } else if (difficult.toLowerCase().equals("h")) {
            System.out.println("Выбрана тяжелая сложность (H - Hard)");
            setDifficult(Difficult.H);
        } else {
            System.out.println("Выбрана некорректная сложность");
        }
    }

    public void startOfTheGame() {
        System.out.println("Начните игру");
        Scanner input = new Scanner(System.in);
        String command = Constants.NEW_GAME;
        if(isSaveFiles(Constants.PATH_TO_SAVES)) {
            System.out.format("[Чтобы начать новую игру, введите %s]", Constants.NEW_GAME);
            System.out.format("[Чтобы загрузить игру, введите %s]\n", Constants.LOAD_GAME);
            command = input.nextLine();
        } else {
            System.out.println("[Доступна только новая игра (нет сохраненных игр)]");
        }

        Game game = new Game();
        switch (command.toUpperCase()) {
            case Constants.NEW_GAME:
                newGame();
                game = this;
                break;
            case Constants.LOAD_GAME:
                System.out.println("[Введите имя файла:]");
                printFiles(Constants.PATH_TO_SAVES);
                String fileName = input.nextLine();
                game = loadGame(Constants.PATH_TO_SAVES + fileName);
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

    private int round(GameWord gameWord, int positionInDict) {
        System.out.println("Внимание, первая ассоциация: ");
        int award = 3;                              // Базовое количество очков
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
                finishTheGame();
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

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.PATH_TO_SAVES + fileName + ".dat"))) {
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + Constants.PATH_TO_SAVES + fileName + " не найден");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFiles(String path) {
        File[] listFiles = new File(path).listFiles();
        if (!isSaveFiles(path)) {
            System.out.println("Нет файлов для загрузки");
        } else {
            assert listFiles != null;
            for (File file : listFiles) {
                System.out.println(file);
            }
        }
    }

    private boolean isSaveFiles(String path) {
        File folder = new File(path);
        File[] listFiles = folder.listFiles();
        return listFiles.length != 0;
    }

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
