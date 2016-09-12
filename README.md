JBuffers
==============
version: 0.1.1

A collection of interfaces, factories, wrapper, and helper classes for java.nio.

Contains:

####ByteBufferQueue
A wrapper for a byte[] with an `offset` and a `length` with removeHead() and removeTail() methods.

####ByteBufferFactory, ByteBufferFactoryImpl
A factory interface and implementation for ByteBuffers

####ByteBufferInterface, ByteBufferWrapper
An interface matching java.nio.ByteBuffer and a wrapper for a ByteBuffer
