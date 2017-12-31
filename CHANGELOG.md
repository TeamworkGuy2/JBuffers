# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
### [0.2.0](N/A) - 2017-12-31
#### Added
* Added `byte[]` constructor to ByteBufferArray
* Added constructor `WritableByteChannelOutputStream(WritableByteChannel channel, ByteBuffer writeBuf)` for complete control
* Added `Pool` class; a generic version of `ByteBufferPool`.  Constructor takes a supplier and consumer to create objects and reset them.

#### Changed
* Upgrade to Java 9
* Upgrade to JUnit 5
* Renamed `ByteBufferQueue` -> `ByteArrayQueue`
* Renamed ByteArrayQueue methods:
  * `getOffset()` -> `offset()`
  * `getLength()` -> `length()`
  * `removeFront()` -> `removeHead()`
  * `removeBack()` -> `removeTail()`
* ByteBufferWrapper now returns the more specific `ByteBufferWrapper` for all of it's chain methods instead of `ByteBufferInterface`
* Additional unit tests

#### Fixed
* Fixed a ByteArrayQueue `removeBack()` bug

--------
### [0.1.2](https://github.com/TeamworkGuy2/JBuffers/commit/92e69ea6a3be43512672fc844b992843d0a36d0f) - 2017-12-02
#### Added
* ByteBufferArray `writeVarInt()` and `readVarInt()` which read/write the minimum number of bytes for a given int using the protobuf variable int encoding format (i.e. 127 reqires 1 byte; 205 requires 2 bytes; 10,750 requires 2 bytes)
* `ByteBufferArray.readUTF(int, byte[], int)` static method to read a UTF string from a buffer excluding the first 2 length bytes

#### Changed
* ByteBufferPool now uses a `BitSet` for it's internal `inUse` flag list to save on memory and Boolean wrapping/unwrapping in old `List<Boolean>` implementation


--------
### [0.1.1](https://github.com/TeamworkGuy2/JBuffers/commit/8c8434888d0cda62141623e0ed040eeb58b9ed81) - 2016-09-11
#### Changed
* Updated dependency path and compiled jar path


--------
### [0.1.0](https://github.com/TeamworkGuy2/JBuffers/commit/df053ce8791db16aa0b827d9f32703049926ac9a) - 2016-07-01
#### Added
* Initial commit of existing code moved from the JCollectionFiller library and other personal projects to this one.
* Includes:
  * ByteBufferArray
  * ByteBufferFactory
  * ByteBufferFactoryImpl
  * ByteBufferInterface
  * ByteBufferPool
  * ByteBufferQueue
  * ByteBufferWrapper
  * CircularByteArray
  * WritableByteChannelOutputStream
