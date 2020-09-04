package entity;

import java.util.List;

public class Solution {
    private List<Integer> answer;

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public static void main(String[] args) {
        List list1 = List.of(1, 2, 3);
        List list2 = List.of(1, 1);
        System.out.println(list1.containsAll(list2));
    }
}
