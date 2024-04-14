A Flink application project using Java and Gradle.

To package your job for submission to Flink, use: 'gradle shadowJar'. Afterwards, you'll find the
jar to use in the 'build/libs' folder.

To run and test your application with an embedded instance of Flink use: 'gradle run'


PS.: Setup done using command from documentation:

```bash
bash -c "$(curl https://flink.apache.org/q/gradle-quickstart.sh)" -- 1.19.0 _2.12
```
