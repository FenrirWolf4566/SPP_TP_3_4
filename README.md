# SPP_TP_3_4

# Performance Analysis

## Speed comparison per number of threads on the same image 

- The image is 1920x1080 pixels. (**2073600 pixels**)
- `k` is the number of threads.
- The speed is the average of 10 runs.

| Engine              | GrayLevelFilter | GaussianContourExtractorFilter |
| ------------------- | --------------- | ------------------------------ |
| Single Thread       | 130ms           | 21026ms                        |
| Multi Thread (k=1)  | 106ms           | 20783ms                        |
| Multi Thread (k=2)  | 76ms            | 10795ms                        |
| Multi Thread (k=3)  | 58ms            | 8590ms                         |
| Multi Thread (k=4)  | 45ms            | 7272ms                         |
| Multi Thread (k=5)  | 40ms            | 6061ms                         |
| Multi Thread (k=6)  | 39ms            | 6639ms                         |
| Multi Thread (k=7)  | 52ms            | 5252ms                         |
| Multi Thread (k=8)  | 35ms            | 6032ms                         |
| Multi Thread (k=9)  | 39ms            | 5128ms                         |
| Multi Thread (k=10) | 41ms            | 5717ms                         |

## Speed comparison per image size on the same number of threads (k=4)

- `n` is the number of pixels.
- The speed is the average of 10 runs.

| Image size                               | GrayLevelFilter | GaussianContourExtractorFilter |
| ---------------------------------------- | --------------- | ------------------------------ |
| 15226222451_75d515f540_o.jpg (n=2073600) | 43ms            | 4919ms                         |
| 15226222451_a49b1a624b_h.jpg (n=1440000) | 23ms            | 3465ms                         |
| 15226222451_5fd668d81a_c.jpg (n=360000)  | 6ms             | 889ms                          |
| 15226222451_5fd668d81a_z.jpg (n=230400)  | 4ms             | 571ms                          |
| 15226222451_5fd668d81a.jpg (n=140500)    | 9ms             | 348ms                          |
| 15226222451_5fd668d81a_n.jpg (n=57280)   | 2ms             | 138ms                          |
| 15226222451_5fd668d81a_m.jpg (n=32160)   | 1ms             | 88ms                           |
| 15226222451_5fd668d81a_t.jpg (n=5600)    | 1ms             | 21ms                           |