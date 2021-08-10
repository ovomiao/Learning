/**
 * [C 语言共用体及其作用]
 *      *【共用体】：在同一块内存区域，存放不同数据类型的数据。
 *      * 共用体所占的内存空间在同一时间内，只能是共用体中某一类型的元素
 *      * 共用体与结构体的区别：
 *          * 共用体中所有成员公用一块内存空间，每次给共用体赋值会
 *            覆盖之前的内容；
 *            结构体所用成员都是独立空间，不存在不同成员间覆盖问题
 *          * 共用体所占的内存空间 = 共用体最长成员的空间大小；
 *            结构体所占空间 >= 所有成员所占空间之和
 * 
 *      * 共用体的一个应用：判断当前系统是使用的 大端存储还是小端存储；
 *          * 【背景知识】操作系统将内存以字节（Byte）为单位划分的，每一个地址单元都对应着一个
 *                      字节（1Byte = 8bit）；即以 8 bit 为一个单元划分内存。
 * 
 */
#include <stdio.h>

/**
 *  声明一个共用体
 *      : 共用体总大小 4Byte
 */
union Person{
    char ch;    // 1Byte
    int num;    // 4Byte
};

/**
* 用于判断是大端存储还是小端存储的共用体
*   ：因为共用体的成员都是共享一块内存空间，而 short 刚好
*     占用 2Byte，即两个地址单元；char 占用 1 个地址单元；
*     用一个 char[2] 数组刚好可以取出一个十六进制数的高位和低位
*     从而判断存储模式
* 
*/
union test {
    short v;
    char index[2];
};

int main()
{
    union Person p;
    printf("共用体的大小：%d Byte.\n", sizeof(union Person));
    p.ch = 'a';  // 共用体所占的内存空间现在存储的是 char 类型的 ’a‘
    printf("共用体 Person 所占的内存空间现在存储的成员 ch 的值： %c\n", p.ch);
    p.num = 590; // 现在共用体所占的内存空间存储的是 int 类型的 590，之前的 ’a‘ 被覆盖了
    printf("共用体 Person 所占的内存空间现在存储的是成员 num 的值：%d\n", p.num);

    printf("\n\n\n");
    union test t;
    t.v = 0x1234;
    printf("index[0] = %d, index[1]=%d\n", t.index[0], t.index[1]);
    if (t.index[0] > t.index[1])
    {
        printf("小端存储\n");
    }
    else
    {
        printf("大端存储\n");
    }
    return 0;
}
