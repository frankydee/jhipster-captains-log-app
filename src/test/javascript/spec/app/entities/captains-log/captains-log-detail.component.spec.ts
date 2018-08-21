/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { CaptainsLogDetailComponent } from 'app/entities/captains-log/captains-log-detail.component';
import { CaptainsLog } from 'app/shared/model/captains-log.model';

describe('Component Tests', () => {
    describe('CaptainsLog Management Detail Component', () => {
        let comp: CaptainsLogDetailComponent;
        let fixture: ComponentFixture<CaptainsLogDetailComponent>;
        const route = ({ data: of({ captainsLog: new CaptainsLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [CaptainsLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CaptainsLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaptainsLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.captainsLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
