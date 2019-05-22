<template>
    <div>
        <Row>
            <i-col span="24">
                <Card>
                    <Button @click="confirmSelection" type="primary">确认选择</Button>&nbsp;
                    <Button @click="showSearchModal" type="primary">高级搜索</Button>&nbsp;
                    <Tooltip content="刷新" placement="right">
                        <Button icon="md-refresh" type="success" shape="circle" @click="searchTable"></Button>
                    </Tooltip>
                    <{zywork.beanName}TableMultiple ref="table" v-on:searchTable="searchTable" v-on:showDetailModal="showDetailModal"/>
                </Card>
            </i-col>
        </Row>
        <{zywork.beanName}SearchModal ref="searchModal" v-on:searchTable="searchTable"/>
        <{zywork.beanName}DetailModal ref="detailModal"/>
    </div>
</template>

<script>
    import * as utils from '@/api/utils-v2'
    import {zywork.beanName}TableMultiple from './{zywork.beanName}TableMultiple.vue'
    import {zywork.beanName}SearchModal from './{zywork.beanName}SearchModal.vue'
    import {zywork.beanName}DetailModal from './{zywork.beanName}DetailModal.vue'
    export default {
        name: '{zywork.beanName}MainMultiple',
        components: {
            {zywork.beanName}TableMultiple,
            {zywork.beanName}SearchModal,
            {zywork.beanName}DetailModal
        },
        data() {
            return {}
        },
        computed: {},
        mounted() {},
        methods: {
            searchTable() {
                utils.search(this)
            },
            showDetailModal(row) {
                let detailModal = this.$refs.detailModal
                detailModal.modal.detail = true
                detailModal.form = row
            },
            showSearchModal() {
                let searchModal = this.$refs.searchModal
                searchModal.modal.search = true
            },
            confirmSelection() {
                // 确认选择的逻辑
            }
        }
    }
</script>

<style>
</style>
