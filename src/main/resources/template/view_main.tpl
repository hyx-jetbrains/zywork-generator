<template>
    <div>
        <Row>
            <i-col span="24">
                <Card>
                    <Button @click="showAddModal" type="primary">添加</Button>&nbsp;
                    <Dropdown @on-click="batchOpt">
                        <Button type="primary">
                            批量操作
                            <Icon type="ios-arrow-down"></Icon>
                        </Button>
                        <DropdownMenu slot="list">
                            <DropdownItem name="batchActive">批量激活</DropdownItem>
                            <DropdownItem name="batchInactive"><span style="color: red;">批量冻结</span></DropdownItem>
                            <DropdownItem name="batchRemove" divided><span style="color: red;">批量删除</span></DropdownItem>
                        </DropdownMenu>
                    </Dropdown>&nbsp;
                    <Button @click="showSearchModal" type="primary">高级搜索</Button>&nbsp;
                    <Tooltip content="刷新" placement="right">
                        <Button icon="md-refresh" type="success" shape="circle" @click="searchTable"></Button>
                    </Tooltip>
                    <{zywork.beanName}TableMain ref="table" v-on:searchTable="searchTable" v-on:showEditModal="showEditModal" v-on:showDetailModal="showDetailModal"/>
                </Card>
            </i-col>
        </Row>
        <{zywork.beanName}AddEditModal ref="addEditModal" v-on:add="add" v-on:edit="edit"/>
        <{zywork.beanName}SearchModal ref="searchModal" v-on:searchTable="searchTable"/>
        <{zywork.beanName}DetailModal ref="detailModal"/>
    </div>
</template>

<script>
    import * as utils from '@/api/utils-v2'
    import {zywork.beanName}TableMain from './{zywork.beanName}TableMain.vue'
    import {zywork.beanName}AddEditModal from './{zywork.beanName}AddEditModal.vue'
    import {zywork.beanName}SearchModal from './{zywork.beanName}SearchModal.vue'
    import {zywork.beanName}DetailModal from './{zywork.beanName}DetailModal.vue'
    export default {
        name: '{zywork.beanName}Main',
        components: {
            {zywork.beanName}TableMain,
            {zywork.beanName}AddEditModal,
            {zywork.beanName}SearchModal,
            {zywork.beanName}DetailModal
        },
        data() {
            return {
                urls: {
                    batchRemoveUrl: '/{zywork.mappingUrl}/admin/batch-remove',
                    batchActiveUrl: '/{zywork.mappingUrl}/admin/batch-active'
                },
            }
        },
        computed: {},
        mounted() {},
        methods: {
            searchTable() {
                utils.search(this)
            },
            showAddModal() {
                let addEditModal = this.$refs.addEditModal
                addEditModal.modal.add = true
            },
            add() {
                utils.add(this)
            },
            showEditModal(row) {
                let addEditModal = this.$refs.addEditModal
                addEditModal.modal.edit = true
                addEditModal.form = row
            },
            edit() {
                utils.edit(this)
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
            batchOpt(itemName) {
                if (itemName === 'batchActive') {
                    utils.batchActive(this, 0)
                } else if (itemName === 'batchInactive') {
                    utils.batchActive(this, 1)
                } else if (itemName === 'batchRemove') {
                    utils.batchRemove(this)
                }
            }
        }
    }
</script>

<style>
</style>
