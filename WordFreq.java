public class WordFreq{

    private String Word;
    private int freq;

    WordFreq(String Word,int freq){
        this.Word = Word;
        this.freq = freq;
    }

    public String toString(){
        return ("the Word " +this.Word+ " appears " +this.freq+ " times in the sentence ");
    }

    public String Key(){
        return this.Word;
    }

    public int Freq(){return this.freq;}

    public void setFreq(int fr){this.freq = fr;}
}