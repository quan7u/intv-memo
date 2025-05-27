## 一、基础

#### hashcode 和 equals

规范：

1. 如果两个对象equals完结果相同，那么hashCode值肯定相等。
2. 如果两个对象的hashCode值不相等，那么这两个对象也不相同。

> 官方注释：每当重写equals方法时，通常都需要重写hashCode方法，以维护hashCode方法的一般约定，即相等的对象必须具有相等的哈希码。

对于依赖哈希容器的需要格外注意。通过正确地覆盖equals()和hashCode()方法，可以确保对象在逻辑上相等时具有相同的哈希码，同时**在哈希表等数据结构中能够正确地存储和检索对象**。

**总的来说还是建议同时重写equals和hashCode方法，保障程序的正确性和健壮性。**





## 二、集合

#### HashMap 与 ConcurrentHashMap 的实现原理是怎样的？

分析：实现原理之类的问题，从底层数据结构、hashCode算法、碰撞解决、扩容机制和线程安全等方面回答

回答：HashMap底层实现由**数组+****链表****+红黑树**组成，引入红黑树的目的是优化查询的时间复杂度为O(logN)

ConcurrentHashMap的实现相对于HashMap有一些不同，它主要采用了分段锁（Segment）的技术

HashMap和ConcurrentHashMap都是Java中常用的哈希表实现，它们的实现原理有所不同。

**HashMap的实现原理：**

- HashMap是非线程安全的，适用于单线程环境。
- HashMap内部使用数组和链表（或红黑树）的组合来实现。数组被分为一定数量的桶（buckets），每个桶可以存储一个或多个键值对。
- 当需要插入或查找元素时，HashMap首先根据键的哈希值确定它应该存储在哪个桶中。然后，如果桶中已经存在元素，则通过比较键的值来进行查找或插入操作。
- 当多个键的哈希值相同时，它们可能会被存储在同一个桶中，形成链表或红黑树。这样可以提高在桶中查找元素的效率。
- HashMap的扩容机制是在桶的数量达到一定的阈值时，将桶的数量扩大一倍，并重新分配元素到新的桶中。

**ConcurrentHashMap**的实现原理：

- ConcurrentHashMap是线程安全的哈希表实现，适用于并发环境。
- ConcurrentHashMap使用了一种分段锁（Segment）的机制来实现并发访问。内部的哈希表被分成一定数量的段（Segment），每个段都是一个独立的哈希表，拥有自己的锁。
- 每个段内部的操作可以并发进行，不同段之间的操作可以并行执行，从而提高并发性能
- ConcurrentHashMap在读取操作时不需要加锁，只有在写入操作时需要加锁，因此读取操作可以并发执行。
- 写入操作时，只需要锁住对应段的锁，而不需要锁住整个哈希表，从而减小了锁的粒度，提高了并发性能。
- ConcurrentHashMap的扩容机制与HashMap类似，不过它是对每个段进行扩容，而不是整个哈希表。

总结起来，HashMap是非线程安全的，适用于单线程环境，而ConcurrentHashMap是线程安全的，适用于并发环境。ConcurrentHashMap通过分段锁的机制实现了并发访问，从而提高了并发性能。





## 三、并发

#### Synchronized 关键字底层是如何实现的？它与 Lock 相比优缺点分别是什么？

**Synchronized**关键字是Java中实现同步的一种方式，在Java的底层实现中采用了`monitor`模型来实现Synchronized，即在每个对象头上维护一个锁的信息(Monitor对象)。

Synchronized最大的优点是简单易用，可以使用关键字`synchronized`来修饰代码块或者方法来达到同步的目的。Synchronized在使用时还有以下优点：

1. 自动释放锁：Synchronized是一种悲观锁，当线程进入同步代码块时，获取锁；当同步代码块执行完后，就会自动释放锁，从而避免了锁被持有时间过长导致的资源浪费等问题。
2. 可重入性：Synchronized是一种可重入锁，同一个线程在获取锁之后可以多次获取同一个锁而不会出现死锁等问题。
3. 隐式锁对象：Synchronized使用的是隐式锁对象，这样可以减少了锁的初始化和存储的成本。

**Lock**实现的是显式锁，需要程序员手动获取和释放锁。Lock相比于Synchronized有以下优点：

1. 可中断：Lock可以响应中断，可以被中断而不会一直占有锁。
2. 公平性：Lock可以通过构造函数设置为公平锁或非公平锁，可以更灵活地满足不同应用场景的需求。
3. 条件变量：Conditional，实现类似wait/notify的功能，可以更灵活地控制线程的等待、唤醒和通知。
4. 性能：在多线程并发的情况下，Lock的性能通常比Synchronized更好。

总之，Synchronized和Lock在同步的实现方式上有所不同，在使用时要根据实际需求来选择。Synchronized的优点是简单易用，可以自动释放锁；而Lock相比之下则在响应中断、控制公平性和实现条件变量时更灵活，但同时也需要手动释放锁，代码实现相对更加复杂。另外，Synchronized的性能也会受到竞争锁对象的锁撞击问题的影响，而Lock可以通过细粒度的控制来避免锁撞击问题，因而在多线程并发的情况下性能通常比较好。

#### 自旋锁的演进

https://coderbee.net/index.php/concurrent/20131115/577

自旋锁（spin）

- 不适合竞争激烈/锁持有时间长的场景下，CPU忙等、内存争用；不适合复杂业务；不保证公平



排队自旋锁

- ticket锁：颁发ticket作为排队号，但仍存在内存争用、性能问题；只适合简单业务

排队自旋锁（链表版）

- MCS锁：显式队列；自旋检查自身节点的状态，依赖前驱节点的通知；释放时需要通知后驱节点
- CLH锁：隐式队列；自旋检查前驱节点的状态；释放时只考虑自身节点

可以看出CLH更简单，AQS也是参考CLH锁来设计的（论文中提到更容易处理取消和超时）



论文翻译：https://www.cnblogs.com/dennyzhangdd/p/7218510.html



设计实现

1. 同步状态
2. 阻塞/取消阻塞
3. 队列
4. 条件队列：**monitor-style**监视器特性，实现一个锁对象可以关联任意数目的条件对象（相比之下，synchronized只支持单一变量，灵活性差）

带着问题看源码

1. 如何加锁？实现tryAcquire编写自定义的加锁逻辑
2. 如何排队？自旋入队（CAS 尾插）
3. 什么时候挂起线程？如何通知？**waitStatus代表后继节点的状态**，后继节点会设置前驱的status为-1，当前驱释放锁时，会unparkSuccessor唤醒后继节点？（这里是从tail往前找pred，直到前面是head）【入队链表操作不是原子的，并发情况下会出现尾分叉】（https://www.zhihu.com/question/50724462）
4. 如何取消排队？



参考资料

论文（The java.util.concurrent Synchronizer Framework）：https://gee.cs.oswego.edu/dl/papers/aqs.pdf

论文翻译：https://www.cnblogs.com/dennyzhangdd/p/7218510.html

javadoop：https://www.javadoop.com/post/AbstractQueuedSynchronizer

monitor-style：一种抽象的同步机制，最早由 Per Brinch Hansen 和 Tony Hoare 在 20 世纪 70 年代提出。

魅族内核团队：https://kernel.meizu.com/2024/03/15/Futex%E6%9C%BA%E5%88%B6%E7%9A%84%E5%86%85%E6%A0%B8%E4%BC%98%E5%8C%96/

linux内核级同步机制--futex：https://github.com/farmerjohngit/myblog/issues/8



#### volatile 关键字解决了什么问题，它的实现原理是什么？

`volatile` 关键字用于修饰变量，主要解决了可见性和有序性的问题。

1. 可见性（Visibility）：当一个线程修改了一个被 `volatile` 修饰的变量的值时，其他线程可以立即看到最新的值，即保证了变量的可见性。这是因为 `volatile` 修饰的变量在每次读取或写入时都会从主内存中读取或刷新，而不是从线程的本地缓存中操作。（JMM）
2. 有序性（Ordering）：`volatile` 关键字保证了指令的有序执行。具体而言，对一个 `volatile` 变量的写操作先于后续的读操作，不会出现指令重排的情况。这样可以确保多个线程按照一定的顺序观察到变量的修改结果。

`volatile` 关键字的**实现原理**是通过在编译器和运行时环境中插入内存屏障（memory barrier）来实现的。内存屏障是一种特殊的指令，它能够确保在其之前的所有读写操作都完成后再执行后续的读写操作。

- 在编译器层面，`volatile` 关键字会告诉编译器在生成的指令中插入内存屏障，以确保指令的有序执行。
- 在运行时环境层面，对 `volatile` 变量的读写会触发内存屏障的操作，保证了可见性和有序性。 读取 `volatile` 变量时，会从主内存中读取最新的值； 写入 `volatile` 变量时，会立即将修改后的值刷新到主内存。

需要注意的是，虽然 `volatile` 关键字解决了可见性和有序性的问题，但它并不能解决原子性的问题。如果需要保证多个操作的原子性，需要使用其他的同步机制，如锁（`synchronized`）或原子类（`Atomic*`）。



#### ThreadLocal 实现原理是什么？

ThreadLocal是Java中的一个线程本地变量，它可以为每个线程提供独立的变量副本，各个线程之间互不影响。ThreadLocal的实现原理主要涉及ThreadLocalMap和Thread类。

1. ThreadLocalMap：每个Thread对象内部都维护着一个ThreadLocalMap对象，它是一个自定义的哈希表，用于存储线程本地变量。ThreadLocalMap的键是ThreadLocal对象，值是对应线程的变量副本。
2. ThreadLocal对象：每个ThreadLocal对象代表一个线程本地变量，它包含一个内部的唯一标识，用于在ThreadLocalMap中查找和存储对应的变量值。
3. 线程访问变量过程：
   1. 当线程通过ThreadLocal的get()方法获取变量时，首先通过当前线程的ThreadLocalMap对象查找对应的变量副本。
   2. 如果在ThreadLocalMap中找到对应的ThreadLocal对象，则返回该对象的值作为变量副本。
   3. 如果在ThreadLocalMap中没有找到对应的ThreadLocal对象，则通过ThreadLocal的initialValue()方法创建一个初始值，并将其存储到ThreadLocalMap中，并返回该初始值作为变量副本。
4. 内存泄漏问题：
   1. 使用ThreadLocal时需要特别注意内存泄漏问题。如果ThreadLocal变量没有及时清理，可能会导致线程结束后，ThreadLocal对象无法被回收，从而造成内存泄漏。可以通过调用ThreadLocal的remove()方法或使用`ThreadLocal.remove()`来手动清理ThreadLocal变量。

总结： ThreadLocal通过为每个线程提供独立的变量副本，实现了线程隔离的效果。其实现原理是通过ThreadLocalMap在每个线程内部维护变量副本，并使用ThreadLocal对象作为键来查找和存储对应的变量值。使用ThreadLocal需要注意内存泄漏问题，及时清理ThreadLocal变量以防止内存泄漏。

参考资料

[ThreadLocal系列之——业务开发实践(一)](https://www.jianshu.com/p/c2cea2285f67)

[ThreadLocal系列之——内存泄露剖析(二)](https://www.jianshu.com/p/9cc71c6a694a)

[ThreadLocal系列之——JDK为内存泄露做的努力(三)](https://www.jianshu.com/p/f135c24a4114)

[ThreadLocal系列之——父子线程传递线程私有数据(四)](https://www.jianshu.com/p/151d13011112)



#### 简述 Java 锁升级的机制

Java中的锁升级机制是指在多线程竞争锁的过程中，锁的状态可以从无锁状态升级为偏向锁、轻量级锁，最终升级为重量级锁的过程。

1. 偏向锁（Biased Locking）：偏向锁是为了在无竞争的情况下提供更低的开销而引入的。当一个线程访问同步块时，会尝试获取偏向锁。如果没有其他线程竞争该锁，获取锁的线程会将对象头中的标记设置为偏向锁，并将线程ID保存在对象头中。下次该线程再次请求锁时，无需进行同步操作，可以直接获取锁。偏向锁的撤销机制是在其他线程尝试获取该锁时，会检查偏向锁的线程ID是否为当前线程，如果不是，则会撤销偏向锁并升级为轻量级锁。
2. 轻量级锁（Lightweight Locking）：轻量级锁是为了在多个线程交替访问同步块时提供更高的性能而引入的。当一个线程请求获取轻量级锁时，JVM会使用CAS操作将对象头中的标记设置为锁记录（Lock Record）指向线程栈帧中的锁记录。如果CAS操作成功，表示该线程获取到了轻量级锁，可以直接进入临界区执行。如果CAS操作失败，说明有其他线程竞争锁，则会进入自旋状态，自旋一定次数后仍未成功，会膨胀为重量级锁。
3. 重量级锁（Heavyweight Locking）：重量级锁是传统的锁机制，使用互斥量（Mutex）实现，即使用操作系统提供的同步原语来实现线程的阻塞和唤醒。当多个线程竞争锁时，会进入阻塞状态，等待锁的释放。重量级锁的使用会带来较大的性能开销，因为涉及线程的切换和内核态与用户态之间的转换。



#### 线程池是如何实现的？简述线程池的任务策略

线程池的主要组成部分包括：

1. 任务队列（Task Queue）：用于存储待执行的任务。线程池中的线程可以从任务队列中获取任务进行执行。
2. 线程池管理器（ThreadPool Manager）：负责创建、启动、停止和管理线程池中的线程。
3. 工作线程（Worker Threads）：线程池中的线程，用于执行任务。
4. 任务（Task）：需要执行的具体任务，可以是实现了`Runnable`接口或`Callable`接口的对象。

线程池的任务策略决定了如何处理任务，常见的策略包括：

1. 直接执行（Direct Execution）：线程池不对任务进行排队，而是直接创建一个新的线程来执行任务。适用于短小的任务，但在高并发情况下可能会导致创建过多的线程，影响性能。

2. 无界队列（Unbounded Queue）：任务队列没有固定的大小限制，可以无限制地添加任务。适用于任务量大、任务执行时间长的情况，但可能导致内存溢出。

3. 有界队列（Bounded Queue）：任务队列有固定的大小限制，当任务队列已满时，新的任务将被拒绝或等待一段时间后重试。适用于平衡任务产生速度和处理速度的情况。（根据线程池的执行逻辑，有界队列会满了才会触发拒绝策略）

4.  拒绝策略（Rejection Policy）：当任务队列已满且无法接受新的任务时，使用拒绝策略来处理新的任务。

   常见的拒绝策略有：

   - Abort Policy：直接抛出`RejectedExecutionException`异常。（不能丢失、强一致；交易下单场景，失败需要补偿）

   - Discard Policy：直接丢弃新的任务。（容忍失败或丢失，常见于日志、埋点等）

   - Discard Oldest Policy：丢弃任务队列中最旧的任务，然后尝试再次提交新的任务。（监控预警）

   - Caller-Runs Policy：由提交任务的线程自己执行该任务。（降低调用方吞吐来保护后端资源）

通过选择合适的任务策略，可以根据实际需求来**平衡线程池的性能和资源消耗**，确保任务的高效执行。







## 四、JVM

### 1、内存划分

### 2、对象分配

### 3、垃圾回收

#### GC root 是什么？

答案：GC root，顾名思义，是指在GC启动的时候必然存活的一组对象。一般来说，GC root 包含：

1. 栈上对象，于 Java 来说，还包含本地方法栈；
2. 全局对象，如常量池；
3. 非收集部分指向收集部分的引用。常见于分代 GC 和增量式GC中





### 4、类加载机制