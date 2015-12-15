import java.io.IOException;
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
      String file = value.toString();
      String [] lines = file.split("\n");

      for(String line : lines)
      {
        if(line.contains("<author>") && line.contains("</author>"))
        {
            String author = line.substring(8, line.indexOf("</a"));
            word.set(author);
            context.write(word, one);
        }
      	else if(line.contains("<author>"))
      	{
                  String author = line.substring(8);
                  word.set(author);
                  context.write(word, one);
      	}
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
}
