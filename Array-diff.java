//https://www.codewars.com/kata/523f5d21c841566fde000009/train/java
//codewar Array.diff

//再升級版，使用set去重過濾並使用stream更高效處理數據
import java.util.*;
import java.util.stream.*;
public class Kata {

  public static int[] arrayDiff(int[] a, int[] b) {
    Set<Integer> setB = Arrays.stream(b) //IntStream
                              .boxed()   //將IntStream轉成Stream<Integer>
                              //.mapToObj(x -> Integer.valueOf(x)) 跟 .boxed等價
                              .collect(Collectors.toSet()); //只吃Stream<T>，所以才要轉化
    
    return Arrays.stream(a)
                 .filter(x -> !setB.contains(x))
                 .toArray();
    
  }
}


/*
//GPT 修改版，本來的迴圈是B包A，但這樣就無法起到使用B陣列去過濾A陣列資料的作用
import java.util.List;
import java.util.ArrayList;
public class Kata {

  public static int[] arrayDiff(int[] a, int[] b) {
    List<Integer> list = new ArrayList<>();
    
    for(int numa : a) {
      boolean found = false;
      
      for(int numb : b) {
        if(numa == numb) {
          found = true;
          break;
        }
      }
      
      if(!found) {
        list.add(numa);
      }
    }
    return list.stream().mapToInt(Integer::valueOf).toArray();
  }
}
*/