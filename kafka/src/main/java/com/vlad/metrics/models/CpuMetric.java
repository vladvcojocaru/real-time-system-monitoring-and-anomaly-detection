// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: metrics.proto
// Protobuf Java Version: 4.28.3

package com.vlad.metrics.models;

/**
 * Protobuf type {@code com.vlad.metrics.models.CpuMetric}
 */
public final class CpuMetric extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:com.vlad.metrics.models.CpuMetric)
    CpuMetricOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 3,
      /* suffix= */ "",
      CpuMetric.class.getName());
  }
  // Use CpuMetric.newBuilder() to construct.
  private CpuMetric(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private CpuMetric() {
    coreLoads_ = emptyDoubleList();
    frequency_ = emptyLongList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Metrics.internal_static_metrics_CpuMetric_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Metrics.internal_static_metrics_CpuMetric_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            CpuMetric.class, CpuMetric.Builder.class);
  }

  public static final int TOTALLOAD_FIELD_NUMBER = 1;
  private double totalLoad_ = 0D;
  /**
   * <code>double totalLoad = 1;</code>
   * @return The totalLoad.
   */
  @java.lang.Override
  public double getTotalLoad() {
    return totalLoad_;
  }

  public static final int CORELOADS_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private com.google.protobuf.Internal.DoubleList coreLoads_ =
      emptyDoubleList();
  /**
   * <code>repeated double coreLoads = 2;</code>
   * @return A list containing the coreLoads.
   */
  @java.lang.Override
  public java.util.List<java.lang.Double>
      getCoreLoadsList() {
    return coreLoads_;
  }
  /**
   * <code>repeated double coreLoads = 2;</code>
   * @return The count of coreLoads.
   */
  public int getCoreLoadsCount() {
    return coreLoads_.size();
  }
  /**
   * <code>repeated double coreLoads = 2;</code>
   * @param index The index of the element to return.
   * @return The coreLoads at the given index.
   */
  public double getCoreLoads(int index) {
    return coreLoads_.getDouble(index);
  }
  private int coreLoadsMemoizedSerializedSize = -1;

  public static final int FREQUENCY_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private com.google.protobuf.Internal.LongList frequency_ =
      emptyLongList();
  /**
   * <code>repeated int64 frequency = 3;</code>
   * @return A list containing the frequency.
   */
  @java.lang.Override
  public java.util.List<java.lang.Long>
      getFrequencyList() {
    return frequency_;
  }
  /**
   * <code>repeated int64 frequency = 3;</code>
   * @return The count of frequency.
   */
  public int getFrequencyCount() {
    return frequency_.size();
  }
  /**
   * <code>repeated int64 frequency = 3;</code>
   * @param index The index of the element to return.
   * @return The frequency at the given index.
   */
  public long getFrequency(int index) {
    return frequency_.getLong(index);
  }
  private int frequencyMemoizedSerializedSize = -1;

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (java.lang.Double.doubleToRawLongBits(totalLoad_) != 0) {
      output.writeDouble(1, totalLoad_);
    }
    if (getCoreLoadsList().size() > 0) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(coreLoadsMemoizedSerializedSize);
    }
    for (int i = 0; i < coreLoads_.size(); i++) {
      output.writeDoubleNoTag(coreLoads_.getDouble(i));
    }
    if (getFrequencyList().size() > 0) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(frequencyMemoizedSerializedSize);
    }
    for (int i = 0; i < frequency_.size(); i++) {
      output.writeInt64NoTag(frequency_.getLong(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (java.lang.Double.doubleToRawLongBits(totalLoad_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(1, totalLoad_);
    }
    {
      int dataSize = 0;
      dataSize = 8 * getCoreLoadsList().size();
      size += dataSize;
      if (!getCoreLoadsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      coreLoadsMemoizedSerializedSize = dataSize;
    }
    {
      int dataSize = 0;
      for (int i = 0; i < frequency_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt64SizeNoTag(frequency_.getLong(i));
      }
      size += dataSize;
      if (!getFrequencyList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      frequencyMemoizedSerializedSize = dataSize;
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof CpuMetric)) {
      return super.equals(obj);
    }
    CpuMetric other = (CpuMetric) obj;

    if (java.lang.Double.doubleToLongBits(getTotalLoad())
        != java.lang.Double.doubleToLongBits(
            other.getTotalLoad())) return false;
    if (!getCoreLoadsList()
        .equals(other.getCoreLoadsList())) return false;
    if (!getFrequencyList()
        .equals(other.getFrequencyList())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TOTALLOAD_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getTotalLoad()));
    if (getCoreLoadsCount() > 0) {
      hash = (37 * hash) + CORELOADS_FIELD_NUMBER;
      hash = (53 * hash) + getCoreLoadsList().hashCode();
    }
    if (getFrequencyCount() > 0) {
      hash = (37 * hash) + FREQUENCY_FIELD_NUMBER;
      hash = (53 * hash) + getFrequencyList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static CpuMetric parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static CpuMetric parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static CpuMetric parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static CpuMetric parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static CpuMetric parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static CpuMetric parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static CpuMetric parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static CpuMetric parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static CpuMetric parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static CpuMetric parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static CpuMetric parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static CpuMetric parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(CpuMetric prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.vlad.metrics.models.CpuMetric}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.vlad.metrics.models.CpuMetric)
          CpuMetricOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Metrics.internal_static_metrics_CpuMetric_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Metrics.internal_static_metrics_CpuMetric_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              CpuMetric.class, CpuMetric.Builder.class);
    }

    // Construct using com.vlad.metrics.models.CpuMetric.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      totalLoad_ = 0D;
      coreLoads_ = emptyDoubleList();
      frequency_ = emptyLongList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Metrics.internal_static_metrics_CpuMetric_descriptor;
    }

    @java.lang.Override
    public CpuMetric getDefaultInstanceForType() {
      return CpuMetric.getDefaultInstance();
    }

    @java.lang.Override
    public CpuMetric build() {
      CpuMetric result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public CpuMetric buildPartial() {
      CpuMetric result = new CpuMetric(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(CpuMetric result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.totalLoad_ = totalLoad_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        coreLoads_.makeImmutable();
        result.coreLoads_ = coreLoads_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        frequency_.makeImmutable();
        result.frequency_ = frequency_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof CpuMetric) {
        return mergeFrom((CpuMetric)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(CpuMetric other) {
      if (other == CpuMetric.getDefaultInstance()) return this;
      if (other.getTotalLoad() != 0D) {
        setTotalLoad(other.getTotalLoad());
      }
      if (!other.coreLoads_.isEmpty()) {
        if (coreLoads_.isEmpty()) {
          coreLoads_ = other.coreLoads_;
          coreLoads_.makeImmutable();
          bitField0_ |= 0x00000002;
        } else {
          ensureCoreLoadsIsMutable();
          coreLoads_.addAll(other.coreLoads_);
        }
        onChanged();
      }
      if (!other.frequency_.isEmpty()) {
        if (frequency_.isEmpty()) {
          frequency_ = other.frequency_;
          frequency_.makeImmutable();
          bitField0_ |= 0x00000004;
        } else {
          ensureFrequencyIsMutable();
          frequency_.addAll(other.frequency_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 9: {
              totalLoad_ = input.readDouble();
              bitField0_ |= 0x00000001;
              break;
            } // case 9
            case 17: {
              double v = input.readDouble();
              ensureCoreLoadsIsMutable();
              coreLoads_.addDouble(v);
              break;
            } // case 17
            case 18: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              int alloc = length > 4096 ? 4096 : length;
              ensureCoreLoadsIsMutable(alloc / 8);
              while (input.getBytesUntilLimit() > 0) {
                coreLoads_.addDouble(input.readDouble());
              }
              input.popLimit(limit);
              break;
            } // case 18
            case 24: {
              long v = input.readInt64();
              ensureFrequencyIsMutable();
              frequency_.addLong(v);
              break;
            } // case 24
            case 26: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              ensureFrequencyIsMutable();
              while (input.getBytesUntilLimit() > 0) {
                frequency_.addLong(input.readInt64());
              }
              input.popLimit(limit);
              break;
            } // case 26
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private double totalLoad_ ;
    /**
     * <code>double totalLoad = 1;</code>
     * @return The totalLoad.
     */
    @java.lang.Override
    public double getTotalLoad() {
      return totalLoad_;
    }
    /**
     * <code>double totalLoad = 1;</code>
     * @param value The totalLoad to set.
     * @return This builder for chaining.
     */
    public Builder setTotalLoad(double value) {

      totalLoad_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>double totalLoad = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalLoad() {
      bitField0_ = (bitField0_ & ~0x00000001);
      totalLoad_ = 0D;
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.DoubleList coreLoads_ = emptyDoubleList();
    private void ensureCoreLoadsIsMutable() {
      if (!coreLoads_.isModifiable()) {
        coreLoads_ = makeMutableCopy(coreLoads_);
      }
      bitField0_ |= 0x00000002;
    }
    private void ensureCoreLoadsIsMutable(int capacity) {
      if (!coreLoads_.isModifiable()) {
        coreLoads_ = makeMutableCopy(coreLoads_, capacity);
      }
      bitField0_ |= 0x00000002;
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @return A list containing the coreLoads.
     */
    public java.util.List<java.lang.Double>
        getCoreLoadsList() {
      coreLoads_.makeImmutable();
      return coreLoads_;
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @return The count of coreLoads.
     */
    public int getCoreLoadsCount() {
      return coreLoads_.size();
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @param index The index of the element to return.
     * @return The coreLoads at the given index.
     */
    public double getCoreLoads(int index) {
      return coreLoads_.getDouble(index);
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @param index The index to set the value at.
     * @param value The coreLoads to set.
     * @return This builder for chaining.
     */
    public Builder setCoreLoads(
        int index, double value) {

      ensureCoreLoadsIsMutable();
      coreLoads_.setDouble(index, value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @param value The coreLoads to add.
     * @return This builder for chaining.
     */
    public Builder addCoreLoads(double value) {

      ensureCoreLoadsIsMutable();
      coreLoads_.addDouble(value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @param values The coreLoads to add.
     * @return This builder for chaining.
     */
    public Builder addAllCoreLoads(
        java.lang.Iterable<? extends java.lang.Double> values) {
      ensureCoreLoadsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, coreLoads_);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>repeated double coreLoads = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCoreLoads() {
      coreLoads_ = emptyDoubleList();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.LongList frequency_ = emptyLongList();
    private void ensureFrequencyIsMutable() {
      if (!frequency_.isModifiable()) {
        frequency_ = makeMutableCopy(frequency_);
      }
      bitField0_ |= 0x00000004;
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @return A list containing the frequency.
     */
    public java.util.List<java.lang.Long>
        getFrequencyList() {
      frequency_.makeImmutable();
      return frequency_;
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @return The count of frequency.
     */
    public int getFrequencyCount() {
      return frequency_.size();
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @param index The index of the element to return.
     * @return The frequency at the given index.
     */
    public long getFrequency(int index) {
      return frequency_.getLong(index);
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @param index The index to set the value at.
     * @param value The frequency to set.
     * @return This builder for chaining.
     */
    public Builder setFrequency(
        int index, long value) {

      ensureFrequencyIsMutable();
      frequency_.setLong(index, value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @param value The frequency to add.
     * @return This builder for chaining.
     */
    public Builder addFrequency(long value) {

      ensureFrequencyIsMutable();
      frequency_.addLong(value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @param values The frequency to add.
     * @return This builder for chaining.
     */
    public Builder addAllFrequency(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureFrequencyIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, frequency_);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 frequency = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFrequency() {
      frequency_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:com.vlad.metrics.models.CpuMetric)
  }

  // @@protoc_insertion_point(class_scope:com.vlad.metrics.models.CpuMetric)
  private static final CpuMetric DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new CpuMetric();
  }

  public static CpuMetric getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CpuMetric>
      PARSER = new com.google.protobuf.AbstractParser<CpuMetric>() {
    @java.lang.Override
    public CpuMetric parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<CpuMetric> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CpuMetric> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public CpuMetric getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

