<template>
    <div>
        <Modal v-model="modal.detail" title="详情" width="760">
            <div class="detail-info">
                {zywork.detailItems}
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: '{zywork.beanName}Detail',
        data() {
            return {
                modal: {
                    detail: false
                },
                form: {
                    {zywork.formFields}
                }
            }
        },
        computed: {},
        mounted() {},
        methods: {}
    }
</script>

<style>
    .detail-info .ivu-col{
        margin-bottom: 10px;
    }

    .detail-title {
        font-weight: bold;
    }
</style>
