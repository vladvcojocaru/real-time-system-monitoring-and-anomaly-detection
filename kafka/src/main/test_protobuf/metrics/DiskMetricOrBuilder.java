// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: metrics.proto
// Protobuf Java Version: 4.28.3

package metrics;

public interface DiskMetricOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.vlad.metrics.models.DiskMetric)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string diskName = 1;</code>
   * @return The diskName.
   */
  java.lang.String getDiskName();
  /**
   * <code>string diskName = 1;</code>
   * @return The bytes for diskName.
   */
  com.google.protobuf.ByteString
      getDiskNameBytes();

  /**
   * <code>int64 diskReads = 2;</code>
   * @return The diskReads.
   */
  long getDiskReads();

  /**
   * <code>int64 diskWrites = 3;</code>
   * @return The diskWrites.
   */
  long getDiskWrites();

  /**
   * <code>int64 diskReadBytes = 4;</code>
   * @return The diskReadBytes.
   */
  long getDiskReadBytes();

  /**
   * <code>int64 diskWriteBytes = 5;</code>
   * @return The diskWriteBytes.
   */
  long getDiskWriteBytes();

  /**
   * <code>int64 diskQueueLength = 6;</code>
   * @return The diskQueueLength.
   */
  long getDiskQueueLength();

  /**
   * <code>int64 diskTransferTime = 7;</code>
   * @return The diskTransferTime.
   */
  long getDiskTransferTime();
}
