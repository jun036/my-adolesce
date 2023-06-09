package com.adolesce.server.meetquestion;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Run {
    private static List<MeetQuestion> meetQuestionList;
    private static Set<Integer> questionNos = new HashSet<>();

    static {
        BufferedReader in = null;
        try {
            meetQuestionList = new ArrayList<>();
            File fileIn = new File("D:/some-test-file/txt/meet-question.txt");
            in = new BufferedReader(new FileReader(fileIn));
            String line = null;
            MeetQuestion child;
            MeetQuestion parent = new MeetQuestion();
            Integer count = 0;

            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                if (StringUtils.isEmpty(line))
                    continue;
                if (!line.startsWith("\t")) {
                    parent = new MeetQuestion();
                    parent.setNo(++count);
                    parent.setQuestion(line.split("\\、")[1]);
                    parent.setIsParent(true);
                    meetQuestionList.add(parent);
                } else if (line.startsWith("\t")) {
                    child = new MeetQuestion();
                    child.setNo(++count);
                    child.setQuestion(StringUtils.trim(line));
                    parent.getChildQuestion().add(child);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append(tip);
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new RuntimeException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        while (true) {
            //主问题集合
            Map<Integer, MeetQuestion> parentMap = meetQuestionList.stream().collect(Collectors.toMap(
                    question -> question.getNo(), Function.identity()
            ));
            //获取主问题编号
            Integer no = getInteger(parentMap);
            //主问题已经获取完毕
            if (Objects.isNull(no)) {
                System.err.println("所有题库已被读取完毕~你觉得自己有没有毕业?");
                break;
            }
            //获取主问题
            MeetQuestion parent = parentMap.get(no);
            //打印主问题
            System.err.println(parent.getQuestion());
            System.err.println("--------------------------------------");
            while (true) {
                String result = scanner("是否继续此话题的相关问题?(Y/N)");
                if (StringUtils.equalsIgnoreCase("Y", result)) {
                    //获取该主问题的子问题集合
                    Map<Integer, MeetQuestion> childMap = parent.getChildQuestion().stream().collect(Collectors.toMap(
                            question -> question.getNo(), Function.identity()
                    ));
                    //获取子问题编号
                    no = getInteger(childMap);
                    //子问题已经获取完毕
                    if (Objects.isNull(no)) {
                        System.err.println("该话题题库已被读取完毕~为您选取另一个话题：");
                        break;
                    }
                    System.err.println(childMap.get(no).getQuestion());
                } else if (StringUtils.equalsIgnoreCase("N", result)) {
                    break;
                }
            }
            continue;
        }
    }

    /**
     * 随机获取问题编号
     *
     * @param questionMap
     * @return
     */
    private static Integer getInteger(Map<Integer, MeetQuestion> questionMap) {
        //问题编号集合
        List<Integer> questionNoList = questionMap.keySet().stream().collect(Collectors.toList());
        //随机获取问题编号
        Integer no = questionNoList.get((int) (Math.random() * (questionNoList.size())));
        //该问题集合是否已经被读取完
        if (questionNos.containsAll(questionNoList)) {
            return null;
        }
        //循环获取问题编号，直至不重复
        while (questionNos.contains(no)) {
            no = (int) (Math.random() * (questionMap.size()));
            no = questionNoList.get(no);
        }
        questionNos.add(no);
        return no;
    }
}

