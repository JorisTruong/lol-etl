# League of Legends ETL project

This is a small ETL project around League of Legends ranked matches using SETL Framework: https://github.com/JCDecaux/setl. The goal of this project is to showcase some features of SETL.

## Dataset

The data is from Kaggle : https://www.kaggle.com/paololol/league-of-legends-ranked-matches/data. The files that are used in this project are ```champs.csv```, ```participants.csv```, ```stats1.csv``` and ```stats2.csv```.

## Reading inputs

As explained in the framework, inputs are read from SparkRepository or Connector as a Delivery.

#### I have a CSV input file with a lot of fields, but I only need a few ones, what can I do ?

You can create a case class containing the fields you want, create a SparkRepository with this case class as type, and setting the config of the CSV file. The example is with the case class ```Player``` which is used to read the  ```matches.csv``` file.

NB : This does not only works with CSV, it also works for Excel or Parquet format.

#### I have multiple partitions of a single CSV input file, how to read all of them into a DataFrame/Dataset ?

You can create a folder, move all the partitions in the folder, and use the path of the folder in the configuration file. The example is with ```statsRepository``` of ```local.conf``` file.

NB : This does not only works with CSV, it also works for Excel or Parquet format.

#### There are too many fields in my input file and I need all of them, should I create a case class of 100 fields to read it ?

For a reason X or Y, you do not always want to create a case class and use a SparkRepository. Instead of reading with a SparkRepository, read with a Connector. You can check out ```CrossDataFactory.scala```.

## Writing outputs
To write the result of a Transformation, you can use a SparkRepository. An example can be found in ```CrossDataFactory.scala```. Sometimes, you do not want to use a SparkRepository because of the case class. Similarly as reading, you can use a Connector. There is an example with the Connector of config ```champPlayersConnector```, which is an output of some Factory that is being saved for future use.


## Structure

#### Transformer, Factory, Stage

**Factory** and **Transformer** are use for Transformations. For example, ```CrossDataFactory``` uses 3 input SparkRepositories, two Transformers (```ChampionPlayerTransformer``` and ```PlayerStatsTransformer```) to produce an output. For the Factory to run its code, we have to add a **Stage** in the **Pipeline**. You can look at ```App.scala``` for more information on that.

The result of the Factory will be passed to the next stage. However, to use it, it has to be "recognizable". That is, if it is unique in the pipeline, like a Dataset of a certain type that only appears once as a result of a Factory. Else, it is not recognizable ; it has to be saved and read from the config as a Delivery.

## Why use SETL ?

I think that SETL simplifies the reading/writing process, optimizes the order of transformations in the pipeline by analyzing dependencies of factories and running them in parallel if they are not inter-dependant. Overall, SETL provides a very clean structure for ETL projects.