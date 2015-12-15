import java.io.IOException;
import java.util.StringTokenizer;

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

public class Order extends Configured implements Tool {

  public int run(String [] args) throws Exception{
    Path tempDir =
        new Path("/user/akhfa/temp");

    Configuration conf = new Configuration();
    Job sortJob = Job.getInstance(conf, "sort");
    FileInputFormat.addInputPath(sortJob, tempDir);
    sortJob.setInputFormatClass(SequenceFileInputFormat.class);
    sortJob.setMapperClass(InverseMapper.class);
    sortJob.setNumReduceTasks(1);                 // write a single file
    FileOutputFormat.setOutputPath(sortJob, new Path(args[1]));
    sortJob.setSortComparatorClass(          // sort by decreasing freq
      LongWritable.DecreasingComparator.class);
    sortJob.setJarByClass(Order.class);
    sortJob.waitForCompletion(true);

    return 0;
  }

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new Order(), args);
  }
}
