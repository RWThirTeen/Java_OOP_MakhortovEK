/* Входные данные для индивидуальных заданий задаются из метода main(),
рекомендуется не делать ручной ввод с консоли, можно просто инициализировать прямо в коде main{}
специально объявленную локальную переменную (или несколько) для задания входных данных.
Все исходные данные (строка или массив) должны попадать внутрь объекта через конструкторы.
В некоторых случаях допустимо передавать часть входных данных в виде аргументов методов решающих задание
        (например, номера слов которые надо поменять местами). Для каждого задания необходимо разработать
минимум один отдельный класс, методы которого и будут выполнять все операции необходимые для решения задания.
Печать результатов должна осуществляться извне методов объекта, т.е. в методе main().

Предложение - это строка содержащая знаки препинания (запятые, точки с запятой) и  оканчивающаяся точкой
    (восклицательным или вопросительным знаком). Слова отделяются друг от друга пробелом.
Между словом и стоящим после него знаком препинания пробел не ставится.

Переставьте местами слова в предложении, под указанными позициями, вводимыми извне.
Знаки препинания должны оставаться на своих местах. */

import java.util.Scanner;

void main()
{
    Scanner scanner = new Scanner(System.in);

    String inputString = "Когда-нибудь ты закроешь сессию без долгов, но это уже совсем другая история.";
    System.out.println("Изначальная строка: " + inputString);

    ArrayList<String> splittedString = new SentenceSplitter(inputString).GetSplittedSentence();

    WordIndexer indexer = new WordIndexer(splittedString);

    int countOfWords = indexer.GetWordsCount();
    int[] swapPositions = new int[2];

    System.out.println("Количество слов в строке: " + countOfWords);

    do
    {
        System.out.println("Введите номер первого слова, которое хотите переместить: ");

        while (!scanner.hasNextInt())
        {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next();
        }

        swapPositions[0] = scanner.nextInt();

        if (swapPositions[0] <= 0 || swapPositions[0] > countOfWords)
        {
            System.out.println("Ошибка! Число должно быть в диапозоне от 1 до " + countOfWords + " включительно.");
        }

    } while (swapPositions[0] <= 0 || swapPositions[0] > countOfWords);

    do
    {
        System.out.println("Введите номер второго слова, которое хотите переместить: ");

        while (!scanner.hasNextInt())
        {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next();
        }

        swapPositions[1] = scanner.nextInt();

        if (swapPositions[1] <= 0 || swapPositions[1] > countOfWords)
        {
            System.out.println("Ошибка! Число должно быть в диапозоне от 1 до " + countOfWords + " включительно.");
        }

        if (swapPositions[0] == swapPositions[1])
        {
            System.out.println("Ошибка! Позиции слов не должны совпадать.");
        }

    } while (swapPositions[1] <= 0 || swapPositions[1] > countOfWords || swapPositions[0] == swapPositions[1]);

    int[] wordIndices = indexer.GetWordIndicesForSwap(swapPositions);

    String resultString = new SentenceShuffler(splittedString).GetStringWithSwappedTwoWords(wordIndices);

    System.out.println("Результирующая строка: \n" + resultString);
}


class SentenceSplitter
{
    private String sentence;


    public ArrayList<String> GetSplittedSentence()
    {
        ArrayList<String> splittedSentence = new ArrayList<>();

        String[] elements = sentence.split(" ");

        for (String e : elements)
        {
            if (!Character.isLetter(e.charAt(e.length() - 1)))
            {
                String part = e.substring(0, e.length() - 1);
                splittedSentence.add(part);
                part = e.substring(e.length()-1);
                splittedSentence.add(part);
            }
            else
            {
                splittedSentence.add(e);
            }
        }

        return splittedSentence;
    }

    public SentenceSplitter(String sourceString)
    {
        sentence = sourceString;
    }
}

class WordIndexer
{
    private ArrayList<String> sentence;
    private ArrayList<Integer> indices = new ArrayList<>();

    public int GetWordsCount()
    {
        if (indices.size() == 0)
            GetWordIndices();

        return indices.size();
    }

    public int[] GetWordIndicesForSwap(int[] positions)
    {
        int[] result = new int[2];

        result[0] = indices.get(positions[0] - 1);
        result[1] = indices.get(positions[1] - 1);

        return result;
    }

    private void GetWordIndices()
    {
        for (int i = 0; i < sentence.size(); i++)
        {
            if (Character.isLetter(sentence.get(i).charAt(0)))
                indices.add(i);
        }
    }

    WordIndexer(ArrayList<String> sentence)
    {
        this.sentence = sentence;
    }
}

class SentenceShuffler
{
    private ArrayList<String> inputSentence;


    public String GetStringWithSwappedTwoWords(int[] positions)
    {
        String resultSentence = "";

        String temp = inputSentence.get(positions[0]);
        inputSentence.set(positions[0], inputSentence.get(positions[1]));
        inputSentence.set(positions[1], temp);

        boolean isFirstWord = true;

        for (String s : inputSentence)
        {
            if (s.length() == 1 && !Character.isLetter(s.charAt(0)))
            {
                resultSentence = resultSentence + s;
            }
            else
            {
                if (isFirstWord)
                {
                    resultSentence = resultSentence + s;
                    isFirstWord = false;
                }
                else resultSentence = resultSentence + " " + s;
            }
        }

        return resultSentence;
    }


    SentenceShuffler(ArrayList<String> sentence)
    {
        inputSentence = new ArrayList<>(sentence);
    }
}