import java.util.*;

public class LeftDelete {
    String RAWString="";
    int SentenceNum=0;
    int DLSNum=0;
    int DLNPum=0;
    int ILSNum=0;
    int ILNNum=0;
    int[] partNum=new int[50];              //每个语句所含原子个数
    String[] Sentences = new String[50];    //语句数组
    String[][] part = new String[50][50];   //语句原子数组
    String[] Extra={"①","②","③","④","⑤"};  //备选非终结符
    Map NonStr=new HashMap();
    int ExtraIndex=0;
    void UpdateNon()
    {
        NonStr=new HashMap();
        for(int i=0;i<SentenceNum;i++)
        {
            if(!NonStr.containsKey(part[i][0]))
                NonStr.put(part[i][0],i+1);
        }
        System.out.println("更新完后的哈希索引:"+NonStr);
    }

    void PRINT()
    {
        System.out.println("----------------------");
        for(int i=0;i<SentenceNum;i++)
        {
            for(int j=0;j<partNum[i];j++)
                System.out.print(part[i][j]+" ");
            System.out.println();
        }
        System.out.println("----------------------");
    }
    void divide(){
        String Compare="";
        Sentences= RAWString.split("\n");
        SentenceNum=0;
        for(int i=0;i<Sentences.length;i++)
        {
            var template =Sentences[i].split("→|->|\\|");
            int j=0;
            if(Compare.equals(template[0]))
                j=1;
            else{
                Compare=template[0];
                SentenceNum++;
            }
           while(j<template.length)
            {
                part[SentenceNum-1][partNum[SentenceNum-1]++]=template[j];
                j++;
            }
        }
PRINT();
    }
 boolean []CacuComplete;
    Set[] NonSolo ;
    void caculatePartNon(){
        CacuComplete = new boolean[NonStr.size()];
        NonSolo = new Set[NonStr.size()];
        for(int i=0;i<NonStr.size();i++)
        {
            NonSolo[i] = new HashSet();
            for(int j=1;j<partNum[i];j++)
            {
                if(part[i][j].charAt(0)<='Z'&&part[i][j].charAt(0)>='A')
                {
                    NonSolo[i].add(part[i][j].charAt(0));
                }
            }
        }
    }
    void SoloComplete(int cur,int indexS){
        System.out.println("cur:"+cur+" indexS:"+indexS);
        Object[]NonSoloArray = NonSolo[indexS].toArray();
        if(CacuComplete[indexS])
        {
            return;
        }
        NonSolo[cur].addAll(NonSolo[indexS]);
        System.out.println("after:"+NonSolo[cur]);
        CacuComplete[indexS]=true;
         for(int i=0;i<NonSoloArray.length;i++)
         {
             System.out.println(NonSolo[indexS]);
             System.out.println("NonSolo[indexS].size():"+NonSolo[indexS].size());
             System.out.println("NonSoloArray[i]:"+NonSoloArray[i]);
             System.out.println(NonStr);
             int next = (int)NonStr.get(String.valueOf(NonSoloArray[i]))-1;
             SoloComplete(cur,next);
         }
         return;
    }
int StartIndirect = 0;
    boolean whetherDLStart(){
        caculatePartNon();
        for(int i=0;i<NonStr.size();i++)
        {
            System.out.println(NonSolo[i]);
            if(NonSolo[i].size()!=0){
                CacuComplete = new boolean[NonStr.size()];
                SoloComplete(i,i);
            }
        }
        System.out.println("---------------------");
        for(int i=0;i<NonStr.size();i++)
        {
            System.out.println(NonSolo[i]);
        }
        System.out.println("---------------------");
        for(int i=0;i<NonStr.size();i++)
        {
            System.out.println("草");
            System.out.println(part[i][0]+"   "+NonSolo[i]);
            if(NonSolo[i].contains(part[i][0].charAt(0)))
            {
                StartIndirect = i;
                System.out.println("存在间接左递归！！！！！！！！！");
                return true;
            }
        }
return false;
    }

    boolean whetherDL()
    {
    for(int i=0;i<SentenceNum;i++)
    {
        for(int j=1;j<partNum[i];j++)
        {
            if(part[i][j].charAt(0)==part[i][0].charAt(0))
            {
                DLSNum=i;
                DLNPum=j;
                return true;
            }
        }
    }
    return false;
    }
    boolean whetherIL()
    {
        System.out.println("StartIndirect:"+StartIndirect);
        for(int i=SentenceNum-1;i>=StartIndirect;i--)
        {
            for(int j=0;j<NonStr.size();j++)
            {
                for(int k=0;k<partNum[j];k++)
                {
                    System.out.print(part[j][k]+" ");
                }
                System.out.println();
            }
            System.out.println(("part[i][0]AD:"+part[i][0]));
            int MAIN = (int)NonStr.get(part[i][0]);
            for(int j=1;j<partNum[i];j++)
            {
                if(part[i][j].charAt(0)>='A'&&part[i][j].charAt(0)<='Z')
                {
                    int COM = (int)NonStr.get(part[i][j].substring(0,1));
                    System.out.println("MAIN:"+MAIN+" COM:"+COM);
                    if(MAIN<COM)
                    {
                        ILSNum=i;
                        ILNNum=MAIN;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    void DirectLeft(){
   while(whetherDL())
   {
    String[] Remain=new String[50];
    String[] NewRow=new String[50];
    int RNum=1;
    Remain[0]=part[DLSNum][0];

    String NewNo=Extra[ExtraIndex++];
       NewRow[0]=NewNo;
       int Nnum=1;
       partNum[SentenceNum]++;
       for(int i=1;i<partNum[DLSNum];i++)
       {
           System.out.println("i:"+i);
           if(part[DLSNum][i].charAt(0)==part[DLSNum][0].charAt(0))
           {

               StringBuffer New=new StringBuffer("");
               New.append(part[DLSNum][i].substring(1));
               New.append(NewNo);
               NewRow[Nnum++]=New.toString();
           }
           else
           {
               StringBuffer New=new StringBuffer("");
               if(!part[DLSNum][i].equals("ε"))
               New.append(part[DLSNum][i]);
               New.append(NewNo);
                Remain[RNum++]=New.toString();
           }
       }
      NewRow[Nnum++]="ε";
       if(RNum==1)
       {
           part[DLSNum]=NewRow;
           partNum[DLSNum]=Nnum;
       }

       else{
           part[DLSNum]=Remain;
           part[SentenceNum]=NewRow;
           partNum[DLSNum]=RNum;
           partNum[SentenceNum++]=Nnum;
       }
       PRINT();
   }
    }
    boolean whetherIndirect(){
        for(int i=0;i<part.length;i++)
        {
            for(int j=1;j<partNum[i];j++)
            {

            }
        }
        return false;
    }
    void IndirectLeft(int start){
        while(whetherIL())
        {
            DirectLeft();
            UpdateNon();
            System.out.println("执行");
            String[] Replace=new String[50];
            int RNum=0;
            for(int i=0;i<partNum[ILSNum];i++)
            {

                if(part[ILSNum][i].charAt(0)>='A'&&part[ILSNum][i].charAt(0)<='Z')
                {
                    int COM = (int)NonStr.get(part[ILSNum][i].substring(0,1));
                    if(ILNNum<COM)
                    {
                        for(int j=1;j<partNum[COM-1];j++)
                        {
                            StringBuffer NewP=new StringBuffer("");
                            NewP.append(part[COM-1][j]);
                            NewP.append(part[ILSNum][i].substring(1));
                            Replace[RNum++]=NewP.toString();
                        }
                    }
                    else
                        Replace[RNum++]=part[ILSNum][i];
                }
                else
                    Replace[RNum++]=part[ILSNum][i];
            }
            part[ILSNum]=Replace;
            partNum[ILSNum]=RNum;
            PRINT();
        }
    }

    String PrintOut()
    {
        StringBuffer Out = new StringBuffer("");
        for(int i=0;i<SentenceNum;i++)
        {
            for(int j=0;j<partNum[i];j++)
            {
                  Out.append(part[i][j]);
                  if(j==0)
                      Out.append("->");
                  else if(j==partNum[i]-1)
                      Out.append("\n");
                  else{
                      Out.append("|");
                  }
            }
        }
        System.out.println(Out.toString());
        return Out.toString();
    }
}
