// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: metrics.proto
// Protobuf Java Version: 4.28.3

package metrics;

public interface OsMetricOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.vlad.metrics.models.OsMetric)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string os_name = 1;</code>
   * @return The osName.
   */
  java.lang.String getOsName();
  /**
   * <code>string os_name = 1;</code>
   * @return The bytes for osName.
   */
  com.google.protobuf.ByteString
      getOsNameBytes();

  /**
   * <code>int64 uptime = 2;</code>
   * @return The uptime.
   */
  long getUptime();

  /**
   * <code>int32 processes = 3;</code>
   * @return The processes.
   */
  int getProcesses();

  /**
   * <code>int32 threads = 4;</code>
   * @return The threads.
   */
  int getThreads();
}
