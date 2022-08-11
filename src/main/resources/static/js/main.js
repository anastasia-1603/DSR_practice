var userApi = Vue.resource('/users{/id}');

Vue.component('user-form', {
    props: ["users"],
    data: function () {
        return {
            surname: '',
            name: '',
            patronymic: '',
            email: ''
        }
    },
    template : '<div>' +
        '<input type="text" placeholder="Write surname" v-model="surname"/>' +
        '<input type="text" placeholder="Write name" v-model="name"/>' +
        '<input type="text" placeholder="Write patronymic" v-model="patronymic"/>' +
        '<input type="text" placeholder="Write email" v-model="email"/>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function() {
            var user = {
                surname: this.surname,
                name: this.name,
                patronymic: this.patronymic,
                email: this.email
            };
            userApi.save({}, user).then(result =>
                result.json().then(data =>
                    this.users.push(data)
                )
            )
        }
    }
})

Vue.component('users-list', {
    props: ['users'],
    template: '<div>' +
        '<user-form :users="users" />' +
        '<div v-for="user in users">{{ user }}</div>' +
        '</div>',
    created: function () {
        userApi.get().then(result =>
            result.json().then( data =>
                data.forEach(user => this.users.push(user))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<users-list :users="users"/>',
    data: {
        users: []
    }
});