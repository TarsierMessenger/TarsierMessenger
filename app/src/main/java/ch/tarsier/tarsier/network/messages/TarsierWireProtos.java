package ch.tarsier.tarsier.network.messages;

public final class TarsierWireProtos {
  private TarsierWireProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface HelloMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:HelloMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required .Peer peer = 1;</code>
     */
    boolean hasPeer();
    /**
     * <code>required .Peer peer = 1;</code>
     */
    TarsierWireProtos.Peer getPeer();
    /**
     * <code>required .Peer peer = 1;</code>
     */
    TarsierWireProtos.PeerOrBuilder getPeerOrBuilder();
  }
  /**
   * Protobuf type {@code HelloMessage}
   */
  public static final class HelloMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:HelloMessage)
      HelloMessageOrBuilder {
    // Use HelloMessage.newBuilder() to construct.
    private HelloMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private HelloMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final HelloMessage defaultInstance;
    public static HelloMessage getDefaultInstance() {
      return defaultInstance;
    }

    public HelloMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private HelloMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              TarsierWireProtos.Peer.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = peer_.toBuilder();
              }
              peer_ = input.readMessage(TarsierWireProtos.Peer.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(peer_);
                peer_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return TarsierWireProtos.internal_static_HelloMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TarsierWireProtos.internal_static_HelloMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              TarsierWireProtos.HelloMessage.class, TarsierWireProtos.HelloMessage.Builder.class);
    }

    public static com.google.protobuf.Parser<HelloMessage> PARSER =
        new com.google.protobuf.AbstractParser<HelloMessage>() {
      public HelloMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new HelloMessage(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<HelloMessage> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int PEER_FIELD_NUMBER = 1;
    private TarsierWireProtos.Peer peer_;
    /**
     * <code>required .Peer peer = 1;</code>
     */
    public boolean hasPeer() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .Peer peer = 1;</code>
     */
    public TarsierWireProtos.Peer getPeer() {
      return peer_;
    }
    /**
     * <code>required .Peer peer = 1;</code>
     */
    public TarsierWireProtos.PeerOrBuilder getPeerOrBuilder() {
      return peer_;
    }

    private void initFields() {
      peer_ = TarsierWireProtos.Peer.getDefaultInstance();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasPeer()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getPeer().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, peer_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, peer_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static TarsierWireProtos.HelloMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.HelloMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static TarsierWireProtos.HelloMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.HelloMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(TarsierWireProtos.HelloMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code HelloMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:HelloMessage)
        TarsierWireProtos.HelloMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TarsierWireProtos.internal_static_HelloMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TarsierWireProtos.internal_static_HelloMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                TarsierWireProtos.HelloMessage.class, TarsierWireProtos.HelloMessage.Builder.class);
      }

      // Construct using TarsierWireProtos.HelloMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getPeerFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (peerBuilder_ == null) {
          peer_ = TarsierWireProtos.Peer.getDefaultInstance();
        } else {
          peerBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TarsierWireProtos.internal_static_HelloMessage_descriptor;
      }

      public TarsierWireProtos.HelloMessage getDefaultInstanceForType() {
        return TarsierWireProtos.HelloMessage.getDefaultInstance();
      }

      public TarsierWireProtos.HelloMessage build() {
        TarsierWireProtos.HelloMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public TarsierWireProtos.HelloMessage buildPartial() {
        TarsierWireProtos.HelloMessage result = new TarsierWireProtos.HelloMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        if (peerBuilder_ == null) {
          result.peer_ = peer_;
        } else {
          result.peer_ = peerBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof TarsierWireProtos.HelloMessage) {
          return mergeFrom((TarsierWireProtos.HelloMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(TarsierWireProtos.HelloMessage other) {
        if (other == TarsierWireProtos.HelloMessage.getDefaultInstance()) return this;
        if (other.hasPeer()) {
          mergePeer(other.getPeer());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasPeer()) {
          
          return false;
        }
        if (!getPeer().isInitialized()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        TarsierWireProtos.HelloMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (TarsierWireProtos.HelloMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private TarsierWireProtos.Peer peer_ = TarsierWireProtos.Peer.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          TarsierWireProtos.Peer, TarsierWireProtos.Peer.Builder, TarsierWireProtos.PeerOrBuilder> peerBuilder_;
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public boolean hasPeer() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public TarsierWireProtos.Peer getPeer() {
        if (peerBuilder_ == null) {
          return peer_;
        } else {
          return peerBuilder_.getMessage();
        }
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public Builder setPeer(TarsierWireProtos.Peer value) {
        if (peerBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          peer_ = value;
          onChanged();
        } else {
          peerBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public Builder setPeer(
          TarsierWireProtos.Peer.Builder builderForValue) {
        if (peerBuilder_ == null) {
          peer_ = builderForValue.build();
          onChanged();
        } else {
          peerBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public Builder mergePeer(TarsierWireProtos.Peer value) {
        if (peerBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001) &&
              peer_ != TarsierWireProtos.Peer.getDefaultInstance()) {
            peer_ =
              TarsierWireProtos.Peer.newBuilder(peer_).mergeFrom(value).buildPartial();
          } else {
            peer_ = value;
          }
          onChanged();
        } else {
          peerBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public Builder clearPeer() {
        if (peerBuilder_ == null) {
          peer_ = TarsierWireProtos.Peer.getDefaultInstance();
          onChanged();
        } else {
          peerBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public TarsierWireProtos.Peer.Builder getPeerBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getPeerFieldBuilder().getBuilder();
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      public TarsierWireProtos.PeerOrBuilder getPeerOrBuilder() {
        if (peerBuilder_ != null) {
          return peerBuilder_.getMessageOrBuilder();
        } else {
          return peer_;
        }
      }
      /**
       * <code>required .Peer peer = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          TarsierWireProtos.Peer, TarsierWireProtos.Peer.Builder, TarsierWireProtos.PeerOrBuilder> 
          getPeerFieldBuilder() {
        if (peerBuilder_ == null) {
          peerBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              TarsierWireProtos.Peer, TarsierWireProtos.Peer.Builder, TarsierWireProtos.PeerOrBuilder>(
                  getPeer(),
                  getParentForChildren(),
                  isClean());
          peer_ = null;
        }
        return peerBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:HelloMessage)
    }

    static {
      defaultInstance = new HelloMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:HelloMessage)
  }

  public interface PeerUpdatedListOrBuilder extends
      // @@protoc_insertion_point(interface_extends:PeerUpdatedList)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    java.util.List<TarsierWireProtos.Peer> 
        getPeerList();
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    TarsierWireProtos.Peer getPeer(int index);
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    int getPeerCount();
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    java.util.List<? extends TarsierWireProtos.PeerOrBuilder> 
        getPeerOrBuilderList();
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    TarsierWireProtos.PeerOrBuilder getPeerOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code PeerUpdatedList}
   */
  public static final class PeerUpdatedList extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:PeerUpdatedList)
      PeerUpdatedListOrBuilder {
    // Use PeerUpdatedList.newBuilder() to construct.
    private PeerUpdatedList(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private PeerUpdatedList(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final PeerUpdatedList defaultInstance;
    public static PeerUpdatedList getDefaultInstance() {
      return defaultInstance;
    }

    public PeerUpdatedList getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private PeerUpdatedList(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                peer_ = new java.util.ArrayList<TarsierWireProtos.Peer>();
                mutable_bitField0_ |= 0x00000001;
              }
              peer_.add(input.readMessage(TarsierWireProtos.Peer.PARSER, extensionRegistry));
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          peer_ = java.util.Collections.unmodifiableList(peer_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return TarsierWireProtos.internal_static_PeerUpdatedList_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TarsierWireProtos.internal_static_PeerUpdatedList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              TarsierWireProtos.PeerUpdatedList.class, TarsierWireProtos.PeerUpdatedList.Builder.class);
    }

    public static com.google.protobuf.Parser<PeerUpdatedList> PARSER =
        new com.google.protobuf.AbstractParser<PeerUpdatedList>() {
      public PeerUpdatedList parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PeerUpdatedList(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<PeerUpdatedList> getParserForType() {
      return PARSER;
    }

    public static final int PEER_FIELD_NUMBER = 1;
    private java.util.List<TarsierWireProtos.Peer> peer_;
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    public java.util.List<TarsierWireProtos.Peer> getPeerList() {
      return peer_;
    }
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    public java.util.List<? extends TarsierWireProtos.PeerOrBuilder> 
        getPeerOrBuilderList() {
      return peer_;
    }
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    public int getPeerCount() {
      return peer_.size();
    }
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    public TarsierWireProtos.Peer getPeer(int index) {
      return peer_.get(index);
    }
    /**
     * <code>repeated .Peer peer = 1;</code>
     */
    public TarsierWireProtos.PeerOrBuilder getPeerOrBuilder(
        int index) {
      return peer_.get(index);
    }

    private void initFields() {
      peer_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      for (int i = 0; i < getPeerCount(); i++) {
        if (!getPeer(i).isInitialized()) {
          memoizedIsInitialized = 0;
          return false;
        }
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < peer_.size(); i++) {
        output.writeMessage(1, peer_.get(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < peer_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, peer_.get(i));
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static TarsierWireProtos.PeerUpdatedList parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.PeerUpdatedList parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static TarsierWireProtos.PeerUpdatedList parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.PeerUpdatedList parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(TarsierWireProtos.PeerUpdatedList prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code PeerUpdatedList}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:PeerUpdatedList)
        TarsierWireProtos.PeerUpdatedListOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TarsierWireProtos.internal_static_PeerUpdatedList_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TarsierWireProtos.internal_static_PeerUpdatedList_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                TarsierWireProtos.PeerUpdatedList.class, TarsierWireProtos.PeerUpdatedList.Builder.class);
      }

      // Construct using TarsierWireProtos.PeerUpdatedList.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getPeerFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (peerBuilder_ == null) {
          peer_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          peerBuilder_.clear();
        }
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TarsierWireProtos.internal_static_PeerUpdatedList_descriptor;
      }

      public TarsierWireProtos.PeerUpdatedList getDefaultInstanceForType() {
        return TarsierWireProtos.PeerUpdatedList.getDefaultInstance();
      }

      public TarsierWireProtos.PeerUpdatedList build() {
        TarsierWireProtos.PeerUpdatedList result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public TarsierWireProtos.PeerUpdatedList buildPartial() {
        TarsierWireProtos.PeerUpdatedList result = new TarsierWireProtos.PeerUpdatedList(this);
        int from_bitField0_ = bitField0_;
        if (peerBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            peer_ = java.util.Collections.unmodifiableList(peer_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.peer_ = peer_;
        } else {
          result.peer_ = peerBuilder_.build();
        }
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof TarsierWireProtos.PeerUpdatedList) {
          return mergeFrom((TarsierWireProtos.PeerUpdatedList)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(TarsierWireProtos.PeerUpdatedList other) {
        if (other == TarsierWireProtos.PeerUpdatedList.getDefaultInstance()) return this;
        if (peerBuilder_ == null) {
          if (!other.peer_.isEmpty()) {
            if (peer_.isEmpty()) {
              peer_ = other.peer_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensurePeerIsMutable();
              peer_.addAll(other.peer_);
            }
            onChanged();
          }
        } else {
          if (!other.peer_.isEmpty()) {
            if (peerBuilder_.isEmpty()) {
              peerBuilder_.dispose();
              peerBuilder_ = null;
              peer_ = other.peer_;
              bitField0_ = (bitField0_ & ~0x00000001);
              peerBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getPeerFieldBuilder() : null;
            } else {
              peerBuilder_.addAllMessages(other.peer_);
            }
          }
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        for (int i = 0; i < getPeerCount(); i++) {
          if (!getPeer(i).isInitialized()) {
            
            return false;
          }
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        TarsierWireProtos.PeerUpdatedList parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (TarsierWireProtos.PeerUpdatedList) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.util.List<TarsierWireProtos.Peer> peer_ =
        java.util.Collections.emptyList();
      private void ensurePeerIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          peer_ = new java.util.ArrayList<TarsierWireProtos.Peer>(peer_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilder<
          TarsierWireProtos.Peer, TarsierWireProtos.Peer.Builder, TarsierWireProtos.PeerOrBuilder> peerBuilder_;

      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public java.util.List<TarsierWireProtos.Peer> getPeerList() {
        if (peerBuilder_ == null) {
          return java.util.Collections.unmodifiableList(peer_);
        } else {
          return peerBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public int getPeerCount() {
        if (peerBuilder_ == null) {
          return peer_.size();
        } else {
          return peerBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public TarsierWireProtos.Peer getPeer(int index) {
        if (peerBuilder_ == null) {
          return peer_.get(index);
        } else {
          return peerBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder setPeer(
          int index, TarsierWireProtos.Peer value) {
        if (peerBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePeerIsMutable();
          peer_.set(index, value);
          onChanged();
        } else {
          peerBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder setPeer(
          int index, TarsierWireProtos.Peer.Builder builderForValue) {
        if (peerBuilder_ == null) {
          ensurePeerIsMutable();
          peer_.set(index, builderForValue.build());
          onChanged();
        } else {
          peerBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder addPeer(TarsierWireProtos.Peer value) {
        if (peerBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePeerIsMutable();
          peer_.add(value);
          onChanged();
        } else {
          peerBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder addPeer(
          int index, TarsierWireProtos.Peer value) {
        if (peerBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensurePeerIsMutable();
          peer_.add(index, value);
          onChanged();
        } else {
          peerBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder addPeer(
          TarsierWireProtos.Peer.Builder builderForValue) {
        if (peerBuilder_ == null) {
          ensurePeerIsMutable();
          peer_.add(builderForValue.build());
          onChanged();
        } else {
          peerBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder addPeer(
          int index, TarsierWireProtos.Peer.Builder builderForValue) {
        if (peerBuilder_ == null) {
          ensurePeerIsMutable();
          peer_.add(index, builderForValue.build());
          onChanged();
        } else {
          peerBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder addAllPeer(
          java.lang.Iterable<? extends TarsierWireProtos.Peer> values) {
        if (peerBuilder_ == null) {
          ensurePeerIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, peer_);
          onChanged();
        } else {
          peerBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder clearPeer() {
        if (peerBuilder_ == null) {
          peer_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          peerBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public Builder removePeer(int index) {
        if (peerBuilder_ == null) {
          ensurePeerIsMutable();
          peer_.remove(index);
          onChanged();
        } else {
          peerBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public TarsierWireProtos.Peer.Builder getPeerBuilder(
          int index) {
        return getPeerFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public TarsierWireProtos.PeerOrBuilder getPeerOrBuilder(
          int index) {
        if (peerBuilder_ == null) {
          return peer_.get(index);  } else {
          return peerBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public java.util.List<? extends TarsierWireProtos.PeerOrBuilder> 
           getPeerOrBuilderList() {
        if (peerBuilder_ != null) {
          return peerBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(peer_);
        }
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public TarsierWireProtos.Peer.Builder addPeerBuilder() {
        return getPeerFieldBuilder().addBuilder(
            TarsierWireProtos.Peer.getDefaultInstance());
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public TarsierWireProtos.Peer.Builder addPeerBuilder(
          int index) {
        return getPeerFieldBuilder().addBuilder(
            index, TarsierWireProtos.Peer.getDefaultInstance());
      }
      /**
       * <code>repeated .Peer peer = 1;</code>
       */
      public java.util.List<TarsierWireProtos.Peer.Builder> 
           getPeerBuilderList() {
        return getPeerFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          TarsierWireProtos.Peer, TarsierWireProtos.Peer.Builder, TarsierWireProtos.PeerOrBuilder> 
          getPeerFieldBuilder() {
        if (peerBuilder_ == null) {
          peerBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              TarsierWireProtos.Peer, TarsierWireProtos.Peer.Builder, TarsierWireProtos.PeerOrBuilder>(
                  peer_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          peer_ = null;
        }
        return peerBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:PeerUpdatedList)
    }

    static {
      defaultInstance = new PeerUpdatedList(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:PeerUpdatedList)
  }

  public interface PeerOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Peer)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required string name = 1;</code>
     */
    boolean hasName();
    /**
     * <code>required string name = 1;</code>
     */
    java.lang.String getName();
    /**
     * <code>required string name = 1;</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();

    /**
     * <code>required bytes publicKey = 2;</code>
     */
    boolean hasPublicKey();
    /**
     * <code>required bytes publicKey = 2;</code>
     */
    com.google.protobuf.ByteString getPublicKey();
  }
  /**
   * Protobuf type {@code Peer}
   */
  public static final class Peer extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:Peer)
      PeerOrBuilder {
    // Use Peer.newBuilder() to construct.
    private Peer(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Peer(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Peer defaultInstance;
    public static Peer getDefaultInstance() {
      return defaultInstance;
    }

    public Peer getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Peer(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              name_ = bs;
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              publicKey_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return TarsierWireProtos.internal_static_Peer_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TarsierWireProtos.internal_static_Peer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              TarsierWireProtos.Peer.class, TarsierWireProtos.Peer.Builder.class);
    }

    public static com.google.protobuf.Parser<Peer> PARSER =
        new com.google.protobuf.AbstractParser<Peer>() {
      public Peer parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Peer(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Peer> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int NAME_FIELD_NUMBER = 1;
    private java.lang.Object name_;
    /**
     * <code>required string name = 1;</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string name = 1;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          name_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PUBLICKEY_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString publicKey_;
    /**
     * <code>required bytes publicKey = 2;</code>
     */
    public boolean hasPublicKey() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required bytes publicKey = 2;</code>
     */
    public com.google.protobuf.ByteString getPublicKey() {
      return publicKey_;
    }

    private void initFields() {
      name_ = "";
      publicKey_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasPublicKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getNameBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, publicKey_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getNameBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, publicKey_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static TarsierWireProtos.Peer parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.Peer parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.Peer parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.Peer parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.Peer parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.Peer parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.Peer parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static TarsierWireProtos.Peer parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.Peer parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.Peer parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(TarsierWireProtos.Peer prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Peer}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Peer)
        TarsierWireProtos.PeerOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TarsierWireProtos.internal_static_Peer_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TarsierWireProtos.internal_static_Peer_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                TarsierWireProtos.Peer.class, TarsierWireProtos.Peer.Builder.class);
      }

      // Construct using TarsierWireProtos.Peer.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        name_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        publicKey_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TarsierWireProtos.internal_static_Peer_descriptor;
      }

      public TarsierWireProtos.Peer getDefaultInstanceForType() {
        return TarsierWireProtos.Peer.getDefaultInstance();
      }

      public TarsierWireProtos.Peer build() {
        TarsierWireProtos.Peer result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public TarsierWireProtos.Peer buildPartial() {
        TarsierWireProtos.Peer result = new TarsierWireProtos.Peer(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.name_ = name_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.publicKey_ = publicKey_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof TarsierWireProtos.Peer) {
          return mergeFrom((TarsierWireProtos.Peer)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(TarsierWireProtos.Peer other) {
        if (other == TarsierWireProtos.Peer.getDefaultInstance()) return this;
        if (other.hasName()) {
          bitField0_ |= 0x00000001;
          name_ = other.name_;
          onChanged();
        }
        if (other.hasPublicKey()) {
          setPublicKey(other.getPublicKey());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasName()) {
          
          return false;
        }
        if (!hasPublicKey()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        TarsierWireProtos.Peer parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (TarsierWireProtos.Peer) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object name_ = "";
      /**
       * <code>required string name = 1;</code>
       */
      public boolean hasName() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string name = 1;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            name_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string name = 1;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string name = 1;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 1;</code>
       */
      public Builder clearName() {
        bitField0_ = (bitField0_ & ~0x00000001);
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 1;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        name_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString publicKey_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes publicKey = 2;</code>
       */
      public boolean hasPublicKey() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required bytes publicKey = 2;</code>
       */
      public com.google.protobuf.ByteString getPublicKey() {
        return publicKey_;
      }
      /**
       * <code>required bytes publicKey = 2;</code>
       */
      public Builder setPublicKey(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        publicKey_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes publicKey = 2;</code>
       */
      public Builder clearPublicKey() {
        bitField0_ = (bitField0_ & ~0x00000002);
        publicKey_ = getDefaultInstance().getPublicKey();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:Peer)
    }

    static {
      defaultInstance = new Peer(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:Peer)
  }

  public interface TarsierPrivateMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:TarsierPrivateMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required bytes cipherText = 1;</code>
     */
    boolean hasCipherText();
    /**
     * <code>required bytes cipherText = 1;</code>
     */
    com.google.protobuf.ByteString getCipherText();

    /**
     * <code>required bytes receiverPublicKey = 2;</code>
     */
    boolean hasReceiverPublicKey();
    /**
     * <code>required bytes receiverPublicKey = 2;</code>
     */
    com.google.protobuf.ByteString getReceiverPublicKey();

    /**
     * <code>required bytes senderPublicKey = 3;</code>
     */
    boolean hasSenderPublicKey();
    /**
     * <code>required bytes senderPublicKey = 3;</code>
     */
    com.google.protobuf.ByteString getSenderPublicKey();

    /**
     * <code>required bytes IV = 4;</code>
     */
    boolean hasIV();
    /**
     * <code>required bytes IV = 4;</code>
     */
    com.google.protobuf.ByteString getIV();

    /**
     * <code>required bytes signature = 5;</code>
     */
    boolean hasSignature();
    /**
     * <code>required bytes signature = 5;</code>
     */
    com.google.protobuf.ByteString getSignature();
  }
  /**
   * Protobuf type {@code TarsierPrivateMessage}
   */
  public static final class TarsierPrivateMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:TarsierPrivateMessage)
      TarsierPrivateMessageOrBuilder {
    // Use TarsierPrivateMessage.newBuilder() to construct.
    private TarsierPrivateMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private TarsierPrivateMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final TarsierPrivateMessage defaultInstance;
    public static TarsierPrivateMessage getDefaultInstance() {
      return defaultInstance;
    }

    public TarsierPrivateMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private TarsierPrivateMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              cipherText_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              receiverPublicKey_ = input.readBytes();
              break;
            }
            case 26: {
              bitField0_ |= 0x00000004;
              senderPublicKey_ = input.readBytes();
              break;
            }
            case 34: {
              bitField0_ |= 0x00000008;
              iV_ = input.readBytes();
              break;
            }
            case 42: {
              bitField0_ |= 0x00000010;
              signature_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return TarsierWireProtos.internal_static_TarsierPrivateMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TarsierWireProtos.internal_static_TarsierPrivateMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              TarsierWireProtos.TarsierPrivateMessage.class, TarsierWireProtos.TarsierPrivateMessage.Builder.class);
    }

    public static com.google.protobuf.Parser<TarsierPrivateMessage> PARSER =
        new com.google.protobuf.AbstractParser<TarsierPrivateMessage>() {
      public TarsierPrivateMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new TarsierPrivateMessage(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<TarsierPrivateMessage> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int CIPHERTEXT_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString cipherText_;
    /**
     * <code>required bytes cipherText = 1;</code>
     */
    public boolean hasCipherText() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bytes cipherText = 1;</code>
     */
    public com.google.protobuf.ByteString getCipherText() {
      return cipherText_;
    }

    public static final int RECEIVERPUBLICKEY_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString receiverPublicKey_;
    /**
     * <code>required bytes receiverPublicKey = 2;</code>
     */
    public boolean hasReceiverPublicKey() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required bytes receiverPublicKey = 2;</code>
     */
    public com.google.protobuf.ByteString getReceiverPublicKey() {
      return receiverPublicKey_;
    }

    public static final int SENDERPUBLICKEY_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString senderPublicKey_;
    /**
     * <code>required bytes senderPublicKey = 3;</code>
     */
    public boolean hasSenderPublicKey() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required bytes senderPublicKey = 3;</code>
     */
    public com.google.protobuf.ByteString getSenderPublicKey() {
      return senderPublicKey_;
    }

    public static final int IV_FIELD_NUMBER = 4;
    private com.google.protobuf.ByteString iV_;
    /**
     * <code>required bytes IV = 4;</code>
     */
    public boolean hasIV() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required bytes IV = 4;</code>
     */
    public com.google.protobuf.ByteString getIV() {
      return iV_;
    }

    public static final int SIGNATURE_FIELD_NUMBER = 5;
    private com.google.protobuf.ByteString signature_;
    /**
     * <code>required bytes signature = 5;</code>
     */
    public boolean hasSignature() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>required bytes signature = 5;</code>
     */
    public com.google.protobuf.ByteString getSignature() {
      return signature_;
    }

    private void initFields() {
      cipherText_ = com.google.protobuf.ByteString.EMPTY;
      receiverPublicKey_ = com.google.protobuf.ByteString.EMPTY;
      senderPublicKey_ = com.google.protobuf.ByteString.EMPTY;
      iV_ = com.google.protobuf.ByteString.EMPTY;
      signature_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasCipherText()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasReceiverPublicKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSenderPublicKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasIV()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSignature()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, cipherText_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, receiverPublicKey_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, senderPublicKey_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeBytes(4, iV_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        output.writeBytes(5, signature_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, cipherText_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, receiverPublicKey_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, senderPublicKey_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, iV_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(5, signature_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.TarsierPrivateMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(TarsierWireProtos.TarsierPrivateMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code TarsierPrivateMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:TarsierPrivateMessage)
        TarsierWireProtos.TarsierPrivateMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TarsierWireProtos.internal_static_TarsierPrivateMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TarsierWireProtos.internal_static_TarsierPrivateMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                TarsierWireProtos.TarsierPrivateMessage.class, TarsierWireProtos.TarsierPrivateMessage.Builder.class);
      }

      // Construct using TarsierWireProtos.TarsierPrivateMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        cipherText_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        receiverPublicKey_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        senderPublicKey_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000004);
        iV_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        signature_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000010);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TarsierWireProtos.internal_static_TarsierPrivateMessage_descriptor;
      }

      public TarsierWireProtos.TarsierPrivateMessage getDefaultInstanceForType() {
        return TarsierWireProtos.TarsierPrivateMessage.getDefaultInstance();
      }

      public TarsierWireProtos.TarsierPrivateMessage build() {
        TarsierWireProtos.TarsierPrivateMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public TarsierWireProtos.TarsierPrivateMessage buildPartial() {
        TarsierWireProtos.TarsierPrivateMessage result = new TarsierWireProtos.TarsierPrivateMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.cipherText_ = cipherText_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.receiverPublicKey_ = receiverPublicKey_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.senderPublicKey_ = senderPublicKey_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.iV_ = iV_;
        if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
          to_bitField0_ |= 0x00000010;
        }
        result.signature_ = signature_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof TarsierWireProtos.TarsierPrivateMessage) {
          return mergeFrom((TarsierWireProtos.TarsierPrivateMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(TarsierWireProtos.TarsierPrivateMessage other) {
        if (other == TarsierWireProtos.TarsierPrivateMessage.getDefaultInstance()) return this;
        if (other.hasCipherText()) {
          setCipherText(other.getCipherText());
        }
        if (other.hasReceiverPublicKey()) {
          setReceiverPublicKey(other.getReceiverPublicKey());
        }
        if (other.hasSenderPublicKey()) {
          setSenderPublicKey(other.getSenderPublicKey());
        }
        if (other.hasIV()) {
          setIV(other.getIV());
        }
        if (other.hasSignature()) {
          setSignature(other.getSignature());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasCipherText()) {
          
          return false;
        }
        if (!hasReceiverPublicKey()) {
          
          return false;
        }
        if (!hasSenderPublicKey()) {
          
          return false;
        }
        if (!hasIV()) {
          
          return false;
        }
        if (!hasSignature()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        TarsierWireProtos.TarsierPrivateMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (TarsierWireProtos.TarsierPrivateMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.ByteString cipherText_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes cipherText = 1;</code>
       */
      public boolean hasCipherText() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bytes cipherText = 1;</code>
       */
      public com.google.protobuf.ByteString getCipherText() {
        return cipherText_;
      }
      /**
       * <code>required bytes cipherText = 1;</code>
       */
      public Builder setCipherText(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        cipherText_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes cipherText = 1;</code>
       */
      public Builder clearCipherText() {
        bitField0_ = (bitField0_ & ~0x00000001);
        cipherText_ = getDefaultInstance().getCipherText();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString receiverPublicKey_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes receiverPublicKey = 2;</code>
       */
      public boolean hasReceiverPublicKey() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required bytes receiverPublicKey = 2;</code>
       */
      public com.google.protobuf.ByteString getReceiverPublicKey() {
        return receiverPublicKey_;
      }
      /**
       * <code>required bytes receiverPublicKey = 2;</code>
       */
      public Builder setReceiverPublicKey(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        receiverPublicKey_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes receiverPublicKey = 2;</code>
       */
      public Builder clearReceiverPublicKey() {
        bitField0_ = (bitField0_ & ~0x00000002);
        receiverPublicKey_ = getDefaultInstance().getReceiverPublicKey();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString senderPublicKey_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes senderPublicKey = 3;</code>
       */
      public boolean hasSenderPublicKey() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required bytes senderPublicKey = 3;</code>
       */
      public com.google.protobuf.ByteString getSenderPublicKey() {
        return senderPublicKey_;
      }
      /**
       * <code>required bytes senderPublicKey = 3;</code>
       */
      public Builder setSenderPublicKey(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        senderPublicKey_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes senderPublicKey = 3;</code>
       */
      public Builder clearSenderPublicKey() {
        bitField0_ = (bitField0_ & ~0x00000004);
        senderPublicKey_ = getDefaultInstance().getSenderPublicKey();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString iV_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes IV = 4;</code>
       */
      public boolean hasIV() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required bytes IV = 4;</code>
       */
      public com.google.protobuf.ByteString getIV() {
        return iV_;
      }
      /**
       * <code>required bytes IV = 4;</code>
       */
      public Builder setIV(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
        iV_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes IV = 4;</code>
       */
      public Builder clearIV() {
        bitField0_ = (bitField0_ & ~0x00000008);
        iV_ = getDefaultInstance().getIV();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString signature_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes signature = 5;</code>
       */
      public boolean hasSignature() {
        return ((bitField0_ & 0x00000010) == 0x00000010);
      }
      /**
       * <code>required bytes signature = 5;</code>
       */
      public com.google.protobuf.ByteString getSignature() {
        return signature_;
      }
      /**
       * <code>required bytes signature = 5;</code>
       */
      public Builder setSignature(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
        signature_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes signature = 5;</code>
       */
      public Builder clearSignature() {
        bitField0_ = (bitField0_ & ~0x00000010);
        signature_ = getDefaultInstance().getSignature();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:TarsierPrivateMessage)
    }

    static {
      defaultInstance = new TarsierPrivateMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:TarsierPrivateMessage)
  }

  public interface TarsierPublicMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:TarsierPublicMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required bytes plainText = 1;</code>
     */
    boolean hasPlainText();
    /**
     * <code>required bytes plainText = 1;</code>
     */
    com.google.protobuf.ByteString getPlainText();

    /**
     * <code>required bytes senderPublicKey = 2;</code>
     */
    boolean hasSenderPublicKey();
    /**
     * <code>required bytes senderPublicKey = 2;</code>
     */
    com.google.protobuf.ByteString getSenderPublicKey();

    /**
     * <code>required bytes signature = 3;</code>
     */
    boolean hasSignature();
    /**
     * <code>required bytes signature = 3;</code>
     */
    com.google.protobuf.ByteString getSignature();
  }
  /**
   * Protobuf type {@code TarsierPublicMessage}
   */
  public static final class TarsierPublicMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:TarsierPublicMessage)
      TarsierPublicMessageOrBuilder {
    // Use TarsierPublicMessage.newBuilder() to construct.
    private TarsierPublicMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private TarsierPublicMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final TarsierPublicMessage defaultInstance;
    public static TarsierPublicMessage getDefaultInstance() {
      return defaultInstance;
    }

    public TarsierPublicMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private TarsierPublicMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              plainText_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              senderPublicKey_ = input.readBytes();
              break;
            }
            case 26: {
              bitField0_ |= 0x00000004;
              signature_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return TarsierWireProtos.internal_static_TarsierPublicMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return TarsierWireProtos.internal_static_TarsierPublicMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              TarsierWireProtos.TarsierPublicMessage.class, TarsierWireProtos.TarsierPublicMessage.Builder.class);
    }

    public static com.google.protobuf.Parser<TarsierPublicMessage> PARSER =
        new com.google.protobuf.AbstractParser<TarsierPublicMessage>() {
      public TarsierPublicMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new TarsierPublicMessage(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<TarsierPublicMessage> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int PLAINTEXT_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString plainText_;
    /**
     * <code>required bytes plainText = 1;</code>
     */
    public boolean hasPlainText() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bytes plainText = 1;</code>
     */
    public com.google.protobuf.ByteString getPlainText() {
      return plainText_;
    }

    public static final int SENDERPUBLICKEY_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString senderPublicKey_;
    /**
     * <code>required bytes senderPublicKey = 2;</code>
     */
    public boolean hasSenderPublicKey() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required bytes senderPublicKey = 2;</code>
     */
    public com.google.protobuf.ByteString getSenderPublicKey() {
      return senderPublicKey_;
    }

    public static final int SIGNATURE_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString signature_;
    /**
     * <code>required bytes signature = 3;</code>
     */
    public boolean hasSignature() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required bytes signature = 3;</code>
     */
    public com.google.protobuf.ByteString getSignature() {
      return signature_;
    }

    private void initFields() {
      plainText_ = com.google.protobuf.ByteString.EMPTY;
      senderPublicKey_ = com.google.protobuf.ByteString.EMPTY;
      signature_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasPlainText()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSenderPublicKey()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSignature()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, plainText_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, senderPublicKey_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, signature_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, plainText_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, senderPublicKey_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, signature_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static TarsierWireProtos.TarsierPublicMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static TarsierWireProtos.TarsierPublicMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(TarsierWireProtos.TarsierPublicMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code TarsierPublicMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:TarsierPublicMessage)
        TarsierWireProtos.TarsierPublicMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return TarsierWireProtos.internal_static_TarsierPublicMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return TarsierWireProtos.internal_static_TarsierPublicMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                TarsierWireProtos.TarsierPublicMessage.class, TarsierWireProtos.TarsierPublicMessage.Builder.class);
      }

      // Construct using TarsierWireProtos.TarsierPublicMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        plainText_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        senderPublicKey_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        signature_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return TarsierWireProtos.internal_static_TarsierPublicMessage_descriptor;
      }

      public TarsierWireProtos.TarsierPublicMessage getDefaultInstanceForType() {
        return TarsierWireProtos.TarsierPublicMessage.getDefaultInstance();
      }

      public TarsierWireProtos.TarsierPublicMessage build() {
        TarsierWireProtos.TarsierPublicMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public TarsierWireProtos.TarsierPublicMessage buildPartial() {
        TarsierWireProtos.TarsierPublicMessage result = new TarsierWireProtos.TarsierPublicMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.plainText_ = plainText_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.senderPublicKey_ = senderPublicKey_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.signature_ = signature_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof TarsierWireProtos.TarsierPublicMessage) {
          return mergeFrom((TarsierWireProtos.TarsierPublicMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(TarsierWireProtos.TarsierPublicMessage other) {
        if (other == TarsierWireProtos.TarsierPublicMessage.getDefaultInstance()) return this;
        if (other.hasPlainText()) {
          setPlainText(other.getPlainText());
        }
        if (other.hasSenderPublicKey()) {
          setSenderPublicKey(other.getSenderPublicKey());
        }
        if (other.hasSignature()) {
          setSignature(other.getSignature());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasPlainText()) {
          
          return false;
        }
        if (!hasSenderPublicKey()) {
          
          return false;
        }
        if (!hasSignature()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        TarsierWireProtos.TarsierPublicMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (TarsierWireProtos.TarsierPublicMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.ByteString plainText_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes plainText = 1;</code>
       */
      public boolean hasPlainText() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bytes plainText = 1;</code>
       */
      public com.google.protobuf.ByteString getPlainText() {
        return plainText_;
      }
      /**
       * <code>required bytes plainText = 1;</code>
       */
      public Builder setPlainText(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        plainText_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes plainText = 1;</code>
       */
      public Builder clearPlainText() {
        bitField0_ = (bitField0_ & ~0x00000001);
        plainText_ = getDefaultInstance().getPlainText();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString senderPublicKey_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes senderPublicKey = 2;</code>
       */
      public boolean hasSenderPublicKey() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required bytes senderPublicKey = 2;</code>
       */
      public com.google.protobuf.ByteString getSenderPublicKey() {
        return senderPublicKey_;
      }
      /**
       * <code>required bytes senderPublicKey = 2;</code>
       */
      public Builder setSenderPublicKey(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        senderPublicKey_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes senderPublicKey = 2;</code>
       */
      public Builder clearSenderPublicKey() {
        bitField0_ = (bitField0_ & ~0x00000002);
        senderPublicKey_ = getDefaultInstance().getSenderPublicKey();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString signature_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes signature = 3;</code>
       */
      public boolean hasSignature() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required bytes signature = 3;</code>
       */
      public com.google.protobuf.ByteString getSignature() {
        return signature_;
      }
      /**
       * <code>required bytes signature = 3;</code>
       */
      public Builder setSignature(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        signature_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes signature = 3;</code>
       */
      public Builder clearSignature() {
        bitField0_ = (bitField0_ & ~0x00000004);
        signature_ = getDefaultInstance().getSignature();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:TarsierPublicMessage)
    }

    static {
      defaultInstance = new TarsierPublicMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:TarsierPublicMessage)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HelloMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_HelloMessage_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_PeerUpdatedList_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_PeerUpdatedList_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Peer_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Peer_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TarsierPrivateMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_TarsierPrivateMessage_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TarsierPublicMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_TarsierPublicMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027TarsierWireProtos.proto\"#\n\014HelloMessag" +
      "e\022\023\n\004peer\030\001 \002(\0132\005.Peer\"&\n\017PeerUpdatedLis" +
      "t\022\023\n\004peer\030\001 \003(\0132\005.Peer\"\'\n\004Peer\022\014\n\004name\030\001" +
      " \002(\t\022\021\n\tpublicKey\030\002 \002(\014\"~\n\025TarsierPrivat" +
      "eMessage\022\022\n\ncipherText\030\001 \002(\014\022\031\n\021receiver" +
      "PublicKey\030\002 \002(\014\022\027\n\017senderPublicKey\030\003 \002(\014" +
      "\022\n\n\002IV\030\004 \002(\014\022\021\n\tsignature\030\005 \002(\014\"U\n\024Tarsi" +
      "erPublicMessage\022\021\n\tplainText\030\001 \002(\014\022\027\n\017se" +
      "nderPublicKey\030\002 \002(\014\022\021\n\tsignature\030\003 \002(\014"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_HelloMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HelloMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_HelloMessage_descriptor,
        new java.lang.String[] { "Peer", });
    internal_static_PeerUpdatedList_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_PeerUpdatedList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_PeerUpdatedList_descriptor,
        new java.lang.String[] { "Peer", });
    internal_static_Peer_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_Peer_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_Peer_descriptor,
        new java.lang.String[] { "Name", "PublicKey", });
    internal_static_TarsierPrivateMessage_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_TarsierPrivateMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_TarsierPrivateMessage_descriptor,
        new java.lang.String[] { "CipherText", "ReceiverPublicKey", "SenderPublicKey", "IV", "Signature", });
    internal_static_TarsierPublicMessage_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_TarsierPublicMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_TarsierPublicMessage_descriptor,
        new java.lang.String[] { "PlainText", "SenderPublicKey", "Signature", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
