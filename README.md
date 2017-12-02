JBuffers
==============
version: 0.1.2

A collection of interfaces, factories, wrapper, and helper classes for java.nio.

## API:

### ByteBufferArray
 - A random access buffer API wrapping a byte[].  Combines the resize-ability of `ByteArrayOutputStream`, the indexed positioning of `ByteBuffer`, and the read/write utility methods of `DataOutput`, as well as `writeVarInt()` and `readVarInt()` which read/write the minimum number of bytes needed for a given int, based on the protobuf variable int encoding format.

### ByteBufferFactory, ByteBufferFactoryImpl
 - A factory interface and implementation for `ByteBuffer`s

### ByteBufferInterface, ByteBufferWrapper
 - An interface matching `java.nio.ByteBuffer` and a wrapper for a ByteBuffer which implements this interface

### ByteBufferPool
 - A manager for a set of `ByteBuffer`s, which provides `hasInstance()`, `getInstance()`, and `returnInstance()` methods and internally maintains a list of buffers with a maximum number of allowed buffers.

### ByteBufferQueue
 - A wrapper for a byte[] with an `offset` and a `length` with `removeHead()` and `removeTail()` methods.
