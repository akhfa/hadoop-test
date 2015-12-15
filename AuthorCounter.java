import java.io.IOException;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// import org.apache.hadoop.conf.Configuration;
// import org.apache.hadoop.fs.Path;
// import org.apache.hadoop.io.IntWritable;
// import org.apache.hadoop.io.Text;
// import org.apache.hadoop.mapreduce.Job;
// import org.apache.hadoop.mapreduce.Mapper;
// import org.apache.hadoop.mapreduce.Reducer;
// import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
// import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.mapreduce.lib.map.*;
import org.apache.hadoop.util.*;

import java.util.Random;

public class AuthorCounter extends Configured implements Tool {

  private String inputPath;

  public void setTheInputPath(String path)
  {
    inputPath = path;
  }

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, LongWritable>{

    private final static LongWritable one = new LongWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
        //StringTokenizer itr = new StringTokenizer(value.toString());
        ArrayList Authors = this.getAllAuthor("dblp-short.xml");
        for(Object Author : Authors)
        {
            word.set(Author.toString());
            context.write(word, one);
        }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,LongWritable,Text,LongWritable> {
    private LongWritable result = new LongWritable();

    public void reduce(Text key, Iterable<LongWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (LongWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public int run(String [] args) throws Exception{
    Path tempDir =
        new Path("/user/akhfa/temp");

    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(AuthorCounter.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(LongWritable.class);
    job.setOutputFormatClass(SequenceFileOutputFormat.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, tempDir);
    System.exit(job.waitForCompletion(true) ? 0 : 1);

    return 0;
  }

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new AuthorCounter(), args);
  }

  public ArrayList getAllAuthor (String filename)
  {
      ArrayList<String> allAuthor = new ArrayList<>();
      // The name of the file to open.
      String fileName = filename;

      // This will reference one line at a time
      String line = null;

      try {
          // FileReader reads text files in the default encoding.
          FileReader fileReader = 
              new FileReader(fileName);

          // Always wrap FileReader in BufferedReader.
          BufferedReader bufferedReader = 
              new BufferedReader(fileReader);

          while((line = bufferedReader.readLine()) != null) {
              if(line.contains("author"))
              {
                  line = line.substring(8, line.indexOf("</a", 7));
                  System.out.println(line);
                  allAuthor.add(line);
              }   
          }   

          // Always close files.
          bufferedReader.close();         
      }
      catch(FileNotFoundException ex) {
          System.out.println(
              "Unable to open file '" + 
              fileName + "'");                
      }
      catch(IOException ex) {
          System.out.println(
              "Error reading file '" 
              + fileName + "'");                  
          // Or we could just do this: 
          // ex.printStackTrace();
      }
      return allAuthor;
  }
}
