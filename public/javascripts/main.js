const { createApp, ref } = Vue


createApp({
    setup() {
        const parallel = ref(false)
        const text = ref('apple kiwi kiwi apple banana kiwi apple banana kiwi peach')
        const submitDisabled = ref(false)
        const submitStatus = ref('pending')
        const mostFrequent = ref([])
        const leastFrequent = ref([])

        const pollMostFrequent = () => {
            fetch('/api/most/').then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        mostFrequent.value = data.words
                    })
                }
            })
            .finally(() => {
                setTimeout(pollMostFrequent, 200)

            })
        }

        const pollLeastFrequent = () => {
            fetch('/api/least/').then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        leastFrequent.value = data.words
                    })
                }
            }).finally(() => {
                setTimeout(pollLeastFrequent, 200)
            })
        }

        pollMostFrequent()
        pollLeastFrequent()

        const handleSubmit = () => {
            submitDisabled.value = true
            submitStatus.value = 'pending'
            fetch('/api/ingest/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    text: text.value,
                    parallel: parallel.value
                })
            }).then(response => {
                submitDisabled.value = false
                if (response.ok) {
                    submitStatus.value = 'success'
                } else {
                    submitStatus.value = 'error'
                }
            })
        }
        return {
            parallel,
            text,
            submitDisabled,
            submitStatus,
            handleSubmit,
            mostFrequent,
            leastFrequent
        }
    }
}).mount('#app')