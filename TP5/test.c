#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 5
#define NUM_INCREMENTS 1000

long shared_variable = 0;
pthread_mutex_t mutex;

void* thread_function(void* arg) {
    int i;
    for (i = 0; i < NUM_INCREMENTS; i++) {
        pthread_mutex_lock(&mutex);
        shared_variable++;
        pthread_mutex_unlock(&mutex);
    }
    pthread_exit(NULL);
}

int main() {
    int i;
    pthread_t threads[NUM_THREADS];
    pthread_mutex_init(&mutex, NULL);

    for (i = 0; i < NUM_THREADS; i++) {
        pthread_create(&threads[i], NULL, thread_function, NULL);
    }

    for (i = 0; i < NUM_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    pthread_mutex_destroy(&mutex);
    printf("Shared variable value: %ld\n", shared_variable);
    return 0;
}